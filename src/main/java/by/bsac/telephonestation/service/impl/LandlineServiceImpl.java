package by.bsac.telephonestation.service.impl;

import by.bsac.telephonestation.dao.LandlineDAO;
import by.bsac.telephonestation.domain.Landline;
import by.bsac.telephonestation.service.LandlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service("landlineService")
public class LandlineServiceImpl implements LandlineService {

    @Autowired
    @Qualifier("landlineDAO")
    private LandlineDAO landlineDAO;

    @Override
    public int findLandlineCountAfterYear(Year year) {
        return landlineDAO.findLandlineCountAfterYear(year);
    }

    @Override
    public int findLandlineNumberByUserSurname(String surname) {
        return landlineDAO.findLandlineNumberByUserSurname(surname);
    }

    @Override
    public List<Landline> findAllLandlines() {
        return landlineDAO.findAllLandlines();
    }

    @Override
    public Landline findEntityById(Long id) {
        return landlineDAO.findEntityById(id);
    }

    @Override
    public boolean remove(Long id) {
        return landlineDAO.remove(id);
    }

    @Override
    public boolean create(Landline landline) {
        return landlineDAO.create(landline);
    }

    @Override
    public boolean update(Landline landline) {
        return landlineDAO.update(landline);
    }
}
