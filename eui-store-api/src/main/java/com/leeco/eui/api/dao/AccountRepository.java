/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leeco.eui.api.entity.Account;

/**
 * @author Bin Gong
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

}
