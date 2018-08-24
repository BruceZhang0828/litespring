package org.litespring.service.v4;

import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.stereotype.Component;

@Component(value = "petStore")
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}
	//private String owner;
    //private int version;



}
