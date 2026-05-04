package university.task.demo.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import university.task.demo.model.dto.GroupCreateRequestDto;
import university.task.demo.model.dto.GroupWithStudentsCountResponseDto;
import university.task.demo.model.entity.Group;
import university.task.demo.util.mapper.GroupMapper;
import university.task.demo.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    private static final long DEFAULT_STUDENTS_COUNT = 0L;

    @Transactional
    public GroupWithStudentsCountResponseDto addGroup(GroupCreateRequestDto groupDto) {
        log.debug("Adding group with number: {}", groupDto.number());
        Group group = groupMapper.toEntity(groupDto);
        groupRepository.save(group);
        log.info("Group saved with id: {}", group.getId());
        return new GroupWithStudentsCountResponseDto(group.getId(), group.getNumber(), DEFAULT_STUDENTS_COUNT);
    }

    @Transactional(readOnly = true)
    public Page<GroupWithStudentsCountResponseDto> getGroups(Pageable pageable) {
        log.debug("Fetching groups with pageable: {}", pageable);
        Page<GroupWithStudentsCountResponseDto> result = groupRepository.findAllWithStudentCount(pageable);
        log.debug("Fetched {} groups", result.getTotalElements());
        return result;
    }

    @Transactional(readOnly = true)
    public boolean groupExists(Long groupId) {
        log.debug("Checking if group exists with id: {}", groupId);
        boolean exists = groupRepository.existsById(groupId);
        log.debug("Group with id: {} exists: {}", groupId, exists);
        return exists;
    }

}
