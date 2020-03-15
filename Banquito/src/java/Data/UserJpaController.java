/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Data.exceptions.IllegalOrphanException;
import Data.exceptions.NonexistentEntityException;
import Data.exceptions.PreexistingEntityException;
import Data.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logic.Cashier;
import java.util.ArrayList;
import java.util.List;
import Logic.Client;
import Logic.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Brazza
 */
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (user.getCashierList() == null) {
            user.setCashierList(new ArrayList<Cashier>());
        }
        if (user.getClientList() == null) {
            user.setClientList(new ArrayList<Client>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cashier> attachedCashierList = new ArrayList<Cashier>();
            for (Cashier cashierListCashierToAttach : user.getCashierList()) {
                cashierListCashierToAttach = em.getReference(cashierListCashierToAttach.getClass(), cashierListCashierToAttach.getId());
                attachedCashierList.add(cashierListCashierToAttach);
            }
            user.setCashierList(attachedCashierList);
            List<Client> attachedClientList = new ArrayList<Client>();
            for (Client clientListClientToAttach : user.getClientList()) {
                clientListClientToAttach = em.getReference(clientListClientToAttach.getClass(), clientListClientToAttach.getId());
                attachedClientList.add(clientListClientToAttach);
            }
            user.setClientList(attachedClientList);
            em.persist(user);
            for (Cashier cashierListCashier : user.getCashierList()) {
                User oldUseridOfCashierListCashier = cashierListCashier.getUserid();
                cashierListCashier.setUserid(user);
                cashierListCashier = em.merge(cashierListCashier);
                if (oldUseridOfCashierListCashier != null) {
                    oldUseridOfCashierListCashier.getCashierList().remove(cashierListCashier);
                    oldUseridOfCashierListCashier = em.merge(oldUseridOfCashierListCashier);
                }
            }
            for (Client clientListClient : user.getClientList()) {
                User oldUseridOfClientListClient = clientListClient.getUserid();
                clientListClient.setUserid(user);
                clientListClient = em.merge(clientListClient);
                if (oldUseridOfClientListClient != null) {
                    oldUseridOfClientListClient.getClientList().remove(clientListClient);
                    oldUseridOfClientListClient = em.merge(oldUseridOfClientListClient);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUser(user.getId()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            List<Cashier> cashierListOld = persistentUser.getCashierList();
            List<Cashier> cashierListNew = user.getCashierList();
            List<Client> clientListOld = persistentUser.getClientList();
            List<Client> clientListNew = user.getClientList();
            List<String> illegalOrphanMessages = null;
            for (Cashier cashierListOldCashier : cashierListOld) {
                if (!cashierListNew.contains(cashierListOldCashier)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cashier " + cashierListOldCashier + " since its userid field is not nullable.");
                }
            }
            for (Client clientListOldClient : clientListOld) {
                if (!clientListNew.contains(clientListOldClient)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Client " + clientListOldClient + " since its userid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cashier> attachedCashierListNew = new ArrayList<Cashier>();
            for (Cashier cashierListNewCashierToAttach : cashierListNew) {
                cashierListNewCashierToAttach = em.getReference(cashierListNewCashierToAttach.getClass(), cashierListNewCashierToAttach.getId());
                attachedCashierListNew.add(cashierListNewCashierToAttach);
            }
            cashierListNew = attachedCashierListNew;
            user.setCashierList(cashierListNew);
            List<Client> attachedClientListNew = new ArrayList<Client>();
            for (Client clientListNewClientToAttach : clientListNew) {
                clientListNewClientToAttach = em.getReference(clientListNewClientToAttach.getClass(), clientListNewClientToAttach.getId());
                attachedClientListNew.add(clientListNewClientToAttach);
            }
            clientListNew = attachedClientListNew;
            user.setClientList(clientListNew);
            user = em.merge(user);
            for (Cashier cashierListNewCashier : cashierListNew) {
                if (!cashierListOld.contains(cashierListNewCashier)) {
                    User oldUseridOfCashierListNewCashier = cashierListNewCashier.getUserid();
                    cashierListNewCashier.setUserid(user);
                    cashierListNewCashier = em.merge(cashierListNewCashier);
                    if (oldUseridOfCashierListNewCashier != null && !oldUseridOfCashierListNewCashier.equals(user)) {
                        oldUseridOfCashierListNewCashier.getCashierList().remove(cashierListNewCashier);
                        oldUseridOfCashierListNewCashier = em.merge(oldUseridOfCashierListNewCashier);
                    }
                }
            }
            for (Client clientListNewClient : clientListNew) {
                if (!clientListOld.contains(clientListNewClient)) {
                    User oldUseridOfClientListNewClient = clientListNewClient.getUserid();
                    clientListNewClient.setUserid(user);
                    clientListNewClient = em.merge(clientListNewClient);
                    if (oldUseridOfClientListNewClient != null && !oldUseridOfClientListNewClient.equals(user)) {
                        oldUseridOfClientListNewClient.getClientList().remove(clientListNewClient);
                        oldUseridOfClientListNewClient = em.merge(oldUseridOfClientListNewClient);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cashier> cashierListOrphanCheck = user.getCashierList();
            for (Cashier cashierListOrphanCheckCashier : cashierListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Cashier " + cashierListOrphanCheckCashier + " in its cashierList field has a non-nullable userid field.");
            }
            List<Client> clientListOrphanCheck = user.getClientList();
            for (Client clientListOrphanCheckClient : clientListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Client " + clientListOrphanCheckClient + " in its clientList field has a non-nullable userid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
