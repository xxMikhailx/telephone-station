package by.bsac.telephonestation.dao;

import by.bsac.telephonestation.domain.Landline;

import java.time.Year;
import java.util.List;

public interface LandlineDAO extends GenericDAO<Long,Landline> {

    int findLandlineCountAfterYear(Year year);

    int findLandlineNumberByUserSurname(String surname);

    List<Landline> findAllLandlines();

    @Override
    Landline findEntityById(Long id);

    @Override
    boolean remove(Long id);

    @Override
    boolean create(Landline landline);

    @Override
    boolean update(Landline landline);
}
