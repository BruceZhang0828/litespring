package org.litespring.service.v1;

import org.litespring.dao.v1.AccountDao;
import org.litespring.dao.v1.ItemDao;
@Data
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
}
