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
import Logic.User;
import Logic.Account;
import Logic.Client;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Brazza
 */
public class ClientJpaController implements Serializable {

    public ClientJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (client.getAccountList() == null) {
            client.setAccountList(new ArrayList<Account>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User userid = client.getUserid();
            if (userid != null) {
                userid = em.getReference(userid.getClass(), userid.getId());
                client.setUserid(userid);
            }
            List<Account> attachedAccountList = new ArrayList<Account>();
            for (Account accountListAccountToAttach : client.getAccountList()) {
                accountListAccountToAttach = em.getReference(accountListAccountToAttach.getClass(), accountListAccountToAttach.getAccountPK());
                attachedAccountList.add(accountListAccountToAttach);
            }
            client.setAccountList(attachedAccountList);
            em.persist(client);
            if (userid != null) {
                userid.getClientList().add(client);
                userid = em.merge(userid);
            }
            for (Account accountListAccount : client.getAccountList()) {
                Client oldClientclientidOfAccountListAccount = accountListAccount.getClientclientid();
                accountListAccount.setClientclientid(client);
                accountListAccount = em.merge(accountListAccount);
                if (oldClientclientidOfAccountListAccount != null) {
                    oldClientclientidOfAccountListAccount.getAccountList().remove(accountListAccount);
                    oldClientclientidOfAccountListAccount = em.merge(oldClientclientidOfAccountListAccount);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findClient(client.getId()) != null) {
                throw new PreexistingEntityException("Client " + client + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client persistentClient = em.find(Client.class, client.getId());
            User useridOld = persistentClient.getUserid();
            User useridNew = client.getUserid();
            List<Account> accountListOld = persistentClient.getAccountList();
            List<Account> accountListNew = client.getAccountList();
            List<String> illegalOrphanMessages = null;
            for (Account accountListOldAccount : accountListOld) {
                if (!accountListNew.contains(accountListOldAccount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Account " + accountListOldAccount + " since its clientclientid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (useridNew != null) {
                useridNew = em.getReference(useridNew.getClass(), useridNew.getId());
                client.setUserid(useridNew);
            }
            List<Account> attachedAccountListNew = new ArrayList<Account>();
            for (Account accountListNewAccountToAttach : accountListNew) {
                accountListNewAccountToAttach = em.getReference(accountListNewAccountToAttach.getClass(), accountListNewAccountToAttach.getAccountPK());
                attachedAccountListNew.add(accountListNewAccountToAttach);
            }
            accountListNew = attachedAccountListNew;
            client.setAccountList(accountListNew);
            client = em.merge(client);
            if (useridOld != null && !useridOld.equals(useridNew)) {
                useridOld.getClientList().remove(client);
                useridOld = em.merge(useridOld);
            }
            if (useridNew != null && !useridNew.equals(useridOld)) {
                useridNew.getClientList().add(client);
                useridNew = em.merge(useridNew);
            }
            for (Account accountListNewAccount : accountListNew) {
                if (!accountListOld.contains(accountListNewAccount)) {
                    Client oldClientclientidOfAccountListNewAccount = accountListNewAccount.getClientclientid();
                    accountListNewAccount.setClientclientid(client);
                    accountListNewAccount = em.merge(accountListNewAccount);
                    if (oldClientclientidOfAccountListNewAccount != null && !oldClientclientidOfAccountListNewAccount.equals(client)) {
                        oldClientclientidOfAccountListNewAccount.getAccountList().remove(accountListNewAccount);
                        oldClientclientidOfAccountListNewAccount = em.merge(oldClientclientidOfAccountListNewAccount);
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
                String id = client.getId();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
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
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Account> accountListOrphanCheck = client.getAccountList();
            for (Account accountListOrphanCheckAccount : accountListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Account " + accountListOrphanCheckAccount + " in its accountList field has a non-nullable clientclientid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userid = client.getUserid();
            if (userid != null) {
                userid.getClientList().remove(client);
                userid = em.merge(userid);
            }
            em.remove(client);
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

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
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

    public Client findClient(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
