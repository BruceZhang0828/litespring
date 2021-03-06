package org.litespring.service.v1;

import lombok.Data;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
@Data
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
}
