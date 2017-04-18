package by.bsac.telephonestation.service;

import by.bsac.telephonestation.domain.Landline;

import java.time.Year;
import java.util.List;

public interface LandlineService {

    int findLandlineCountAfterYear(Year year);

    int findLandlineNumberByUserSurname(String surname);

    List<Landline> findAllLandlines();

    Landline findEntityById(Long id);

    boolean remove(Long id);

    boolean create(Landline landline);

    boolean update(Landline landline);
}
