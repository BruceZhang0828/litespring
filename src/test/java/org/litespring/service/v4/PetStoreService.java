package org.litespring.service.v4;

import lombok.Data;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;

@Data
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    //private String owner;
    //private int version;


    public PetStoreService(AccountDao accountDao, ItemDao itemDao){
        		this.accountDao = accountDao;
        		this.itemDao = itemDao;
        		// this.version = -1;
        	}
	public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version){
        		this.accountDao = accountDao;
        		this.itemDao = itemDao;
        		//this.version = version;
    }
}
