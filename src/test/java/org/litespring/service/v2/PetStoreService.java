package org.litespring.service.v2;

import lombok.Data;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;

@Data
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private String owner;
    private int version;
}
