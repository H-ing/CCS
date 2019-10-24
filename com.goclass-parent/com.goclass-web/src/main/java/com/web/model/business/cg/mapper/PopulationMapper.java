package com.web.model.business.cg.mapper;

import java.util.List;
import java.util.Map;

public interface PopulationMapper {
	int selectNumberOfStudentByCombinationId(int id);
	int selectNumberOfTeacherBySubjectId(int id);
}
