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
import Logic.Account;
import Logic.Currency;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Brazza
 */
public class CurrencyJpaController implements Serializable {

    public CurrencyJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Currency currency) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (currency.getAccountList() == null) {
            currency.setAccountList(new ArrayList<Account>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Account> attachedAccountList = new ArrayList<Account>();
            for (Account accountListAccountToAttach : currency.getAccountList()) {
                accountListAccountToAttach = em.getReference(accountListAccountToAttach.getClass(), accountListAccountToAttach.getAccountPK());
                attachedAccountList.add(accountListAccountToAttach);
            }
            currency.setAccountList(attachedAccountList);
            em.persist(currency);
            for (Account accountListAccount : currency.getAccountList()) {
                Currency oldCurrencycurrencyCode1OfAccountListAccount = accountListAccount.getCurrencycurrencyCode1();
                accountListAccount.setCurrencycurrencyCode1(currency);
                accountListAccount = em.merge(accountListAccount);
                if (oldCurrencycurrencyCode1OfAccountListAccount != null) {
                    oldCurrencycurrencyCode1OfAccountListAccount.getAccountList().remove(accountListAccount);
                    oldCurrencycurrencyCode1OfAccountListAccount = em.merge(oldCurrencycurrencyCode1OfAccountListAccount);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCurrency(currency.getCurrencyCode()) != null) {
                throw new PreexistingEntityException("Currency " + currency + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Currency currency) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Currency persistentCurrency = em.find(Currency.class, currency.getCurrencyCode());
            List<Account> accountListOld = persistentCurrency.getAccountList();
            List<Account> accountListNew = currency.getAccountList();
            List<String> illegalOrphanMessages = null;
            for (Account accountListOldAccount : accountListOld) {
                if (!accountListNew.contains(accountListOldAccount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Account " + accountListOldAccount + " since its currencycurrencyCode1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Account> attachedAccountListNew = new ArrayList<Account>();
            for (Account accountListNewAccountToAttach : accountListNew) {
                accountListNewAccountToAttach = em.getReference(accountListNewAccountToAttach.getClass(), accountListNewAccountToAttach.getAccountPK());
                attachedAccountListNew.add(accountListNewAccountToAttach);
            }
            accountListNew = attachedAccountListNew;
            currency.setAccountList(accountListNew);
            currency = em.merge(currency);
            for (Account accountListNewAccount : accountListNew) {
                if (!accountListOld.contains(accountListNewAccount)) {
                    Currency oldCurrencycurrencyCode1OfAccountListNewAccount = accountListNewAccount.getCurrencycurrencyCode1();
                    accountListNewAccount.setCurrencycurrencyCode1(currency);
                    accountListNewAccount = em.merge(accountListNewAccount);
                    if (oldCurrencycurrencyCode1OfAccountListNewAccount != null && !oldCurrencycurrencyCode1OfAccountListNewAccount.equals(currency)) {
                        oldCurrencycurrencyCode1OfAccountListNewAccount.getAccountList().remove(accountListNewAccount);
                        oldCurrencycurrencyCode1OfAccountListNewAccount = em.merge(oldCurrencycurrencyCode1OfAccountListNewAccount);
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
                String id = currency.getCurrencyCode();
                if (findCurrency(id) == null) {
                    throw new NonexistentEntityException("The currency with id " + id + " no longer exists.");
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
            Currency currency;
            try {
                currency = em.getReference(Currency.class, id);
                currency.getCurrencyCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The currency with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Account> accountListOrphanCheck = currency.getAccountList();
            for (Account accountListOrphanCheckAccount : accountListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Currency (" + currency + ") cannot be destroyed since the Account " + accountListOrphanCheckAccount + " in its accountList field has a non-nullable currencycurrencyCode1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(currency);
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

    public List<Currency> findCurrencyEntities() {
        return findCurrencyEntities(true, -1, -1);
    }

    public List<Currency> findCurrencyEntities(int maxResults, int firstResult) {
        return findCurrencyEntities(false, maxResults, firstResult);
    }

    private List<Currency> findCurrencyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Currency.class));
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

    public Currency findCurrency(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Currency.class, id);
        } finally {
            em.close();
        }
    }

    public int getCurrencyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Currency> rt = cq.from(Currency.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
