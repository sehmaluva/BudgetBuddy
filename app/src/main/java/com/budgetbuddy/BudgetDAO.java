package com.budgetbuddy;

import androidx.room.*;


@Dao
public interface BudgetDAO {
    @Insert
    void insertUser(Budget budget);

    @Query("SELECT * FROM Users WHERE email = :email LIMIT 1")
    Budget getUserByEmail(String email);

    @Update
    void updateUser(Budget budget);

    @Query("SELECT COUNT(*) FROM Users WHERE email = :email")
    int userExists(String email);
}
