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
import Logic.Account;
import Logic.AccountPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logic.Client;
import Logic.Currency;
import Logic.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Brazza
 */
public class AccountJpaController implements Serializable {

    public AccountJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Account account) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (account.getAccountPK() == null) {
            account.setAccountPK(new AccountPK());
        }
        if (account.getTransactionList() == null) {
            account.setTransactionList(new ArrayList<Transaction>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client clientclientid = account.getClientclientid();
            if (clientclientid != null) {
                clientclientid = em.getReference(clientclientid.getClass(), clientclientid.getId());
                account.setClientclientid(clientclientid);
            }
            Currency currencycurrencyCode1 = account.getCurrencycurrencyCode1();
            if (currencycurrencyCode1 != null) {
                currencycurrencyCode1 = em.getReference(currencycurrencyCode1.getClass(), currencycurrencyCode1.getCurrencyCode());
                account.setCurrencycurrencyCode1(currencycurrencyCode1);
            }
            List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
            for (Transaction transactionListTransactionToAttach : account.getTransactionList()) {
                transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(), transactionListTransactionToAttach.getNumber());
                attachedTransactionList.add(transactionListTransactionToAttach);
            }
            account.setTransactionList(attachedTransactionList);
            em.persist(account);
            if (clientclientid != null) {
                clientclientid.getAccountList().add(account);
                clientclientid = em.merge(clientclientid);
            }
            if (currencycurrencyCode1 != null) {
                currencycurrencyCode1.getAccountList().add(account);
                currencycurrencyCode1 = em.merge(currencycurrencyCode1);
            }
            for (Transaction transactionListTransaction : account.getTransactionList()) {
                Account oldAccountOfTransactionListTransaction = transactionListTransaction.getAccount();
                transactionListTransaction.setAccount(account);
                transactionListTransaction = em.merge(transactionListTransaction);
                if (oldAccountOfTransactionListTransaction != null) {
                    oldAccountOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
                    oldAccountOfTransactionListTransaction = em.merge(oldAccountOfTransactionListTransaction);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAccount(account.getAccountPK()) != null) {
                throw new PreexistingEntityException("Account " + account + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account persistentAccount = em.find(Account.class, account.getAccountPK());
            Client clientclientidOld = persistentAccount.getClientclientid();
            Client clientclientidNew = account.getClientclientid();
            Currency currencycurrencyCode1Old = persistentAccount.getCurrencycurrencyCode1();
            Currency currencycurrencyCode1New = account.getCurrencycurrencyCode1();
            List<Transaction> transactionListOld = persistentAccount.getTransactionList();
            List<Transaction> transactionListNew = account.getTransactionList();
            List<String> illegalOrphanMessages = null;
            for (Transaction transactionListOldTransaction : transactionListOld) {
                if (!transactionListNew.contains(transactionListOldTransaction)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaction " + transactionListOldTransaction + " since its account field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientclientidNew != null) {
                clientclientidNew = em.getReference(clientclientidNew.getClass(), clientclientidNew.getId());
                account.setClientclientid(clientclientidNew);
            }
            if (currencycurrencyCode1New != null) {
                currencycurrencyCode1New = em.getReference(currencycurrencyCode1New.getClass(), currencycurrencyCode1New.getCurrencyCode());
                account.setCurrencycurrencyCode1(currencycurrencyCode1New);
            }
            List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
            for (Transaction transactionListNewTransactionToAttach : transactionListNew) {
                transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(), transactionListNewTransactionToAttach.getNumber());
                attachedTransactionListNew.add(transactionListNewTransactionToAttach);
            }
            transactionListNew = attachedTransactionListNew;
            account.setTransactionList(transactionListNew);
            account = em.merge(account);
            if (clientclientidOld != null && !clientclientidOld.equals(clientclientidNew)) {
                clientclientidOld.getAccountList().remove(account);
                clientclientidOld = em.merge(clientclientidOld);
            }
            if (clientclientidNew != null && !clientclientidNew.equals(clientclientidOld)) {
                clientclientidNew.getAccountList().add(account);
                clientclientidNew = em.merge(clientclientidNew);
            }
            if (currencycurrencyCode1Old != null && !currencycurrencyCode1Old.equals(currencycurrencyCode1New)) {
                currencycurrencyCode1Old.getAccountList().remove(account);
                currencycurrencyCode1Old = em.merge(currencycurrencyCode1Old);
            }
            if (currencycurrencyCode1New != null && !currencycurrencyCode1New.equals(currencycurrencyCode1Old)) {
                currencycurrencyCode1New.getAccountList().add(account);
                currencycurrencyCode1New = em.merge(currencycurrencyCode1New);
            }
            for (Transaction transactionListNewTransaction : transactionListNew) {
                if (!transactionListOld.contains(transactionListNewTransaction)) {
                    Account oldAccountOfTransactionListNewTransaction = transactionListNewTransaction.getAccount();
                    transactionListNewTransaction.setAccount(account);
                    transactionListNewTransaction = em.merge(transactionListNewTransaction);
                    if (oldAccountOfTransactionListNewTransaction != null && !oldAccountOfTransactionListNewTransaction.equals(account)) {
                        oldAccountOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
                        oldAccountOfTransactionListNewTransaction = em.merge(oldAccountOfTransactionListNewTransaction);
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
                AccountPK id = account.getAccountPK();
                if (findAccount(id) == null) {
                    throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AccountPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account account;
            try {
                account = em.getReference(Account.class, id);
                account.getAccountPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Transaction> transactionListOrphanCheck = account.getTransactionList();
            for (Transaction transactionListOrphanCheckTransaction : transactionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Transaction " + transactionListOrphanCheckTransaction + " in its transactionList field has a non-nullable account field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client clientclientid = account.getClientclientid();
            if (clientclientid != null) {
                clientclientid.getAccountList().remove(account);
                clientclientid = em.merge(clientclientid);
            }
            Currency currencycurrencyCode1 = account.getCurrencycurrencyCode1();
            if (currencycurrencyCode1 != null) {
                currencycurrencyCode1.getAccountList().remove(account);
                currencycurrencyCode1 = em.merge(currencycurrencyCode1);
            }
            em.remove(account);
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

    public List<Account> findAccountEntities() {
        return findAccountEntities(true, -1, -1);
    }

    public List<Account> findAccountEntities(int maxResults, int firstResult) {
        return findAccountEntities(false, maxResults, firstResult);
    }

    private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Account.class));
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

    public Account findAccount(AccountPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Account.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Account> rt = cq.from(Account.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
