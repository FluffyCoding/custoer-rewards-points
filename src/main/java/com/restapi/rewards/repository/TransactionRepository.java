package com.restapi.rewards.repository;

import com.restapi.rewards.dto.CustomersTransactionSummryDto;
import com.restapi.rewards.dto.RewardPointsResponseDto;
import com.restapi.rewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /***
     * // this method collects points of a customer by dates and returns it.
     * @param id  customer id
     * @param dateStart  date from
     * @param dateEnd    date to
     * @return it returns name , email , totalpoints
     */
    @Query(value = "SELECT c.first_name as name ,c.email as email , SUM(t.transaction_amount) as totalAmountSpent,SUM(t.points_awarded) as totalPointsEarnd " +
            "FROM customer c JOIN transactions t ON c.id = t.customer_id " +
            "WHERE c.id = ?1 AND t.transaction_date BETWEEN ?2 AND ?3", nativeQuery = true)
    RewardPointsResponseDto collectPointsByCustomerId(Long id, LocalDate dateStart, LocalDate dateEnd);

    /***
     * this method returns the total transactions of each customer.
     * @param dateStart start Date
     * @param dateEnd end Date
     * @return
     */
    @Query(nativeQuery = true,value = """
            select c.first_name                as customerName,
                   SUM(t.points_awarded)          totalPointsEarned,
                   SUM(t.transaction_amount)   as totalAmoutSpent,
                   COUNT(t.transaction_amount) as totalOrdersPlaced
            from transactions t
                     join customer c on t.customer_id = c.id
            WHERE t.transaction_date between ?1 AND ?2
            GROUP BY (c.id)
            ORDER BY (totalPointsEarned) DESC;
            """)
    List<CustomersTransactionSummryDto> getTransactionsSummaryByDate(LocalDate dateStart, LocalDate dateEnd);

    List<Transaction> findAllByCustomer_Email(String email);

    List<Transaction> findByCustomer_IdAndTransactionDateBetweenOrderByTransactionDateAsc(Long id, LocalDate transactionDateStart, LocalDate transactionDateEnd);


}