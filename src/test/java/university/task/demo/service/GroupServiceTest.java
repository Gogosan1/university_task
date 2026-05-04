package university.task.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import university.task.demo.model.dto.GroupCreateRequestDto;
import university.task.demo.model.dto.GroupWithStudentsCountResponseDto;
import university.task.demo.model.entity.Group;
import university.task.demo.util.mapper.GroupMapper;
import university.task.demo.repository.GroupRepository;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupService groupService;

    private GroupCreateRequestDto groupCreateRequestDto;
    private Group group;
    private GroupWithStudentsCountResponseDto dto;

    @BeforeEach
    void setUp() {
        groupCreateRequestDto = new GroupCreateRequestDto("101");
        group = new Group();
        group.setId(1L);
        group.setNumber("101");
        dto = new GroupWithStudentsCountResponseDto(1L, "101", 0L);
    }

    @Test
    void addGroup_savesAndReturnsGroup() {
        when(groupMapper.toEntity(groupCreateRequestDto)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);

        GroupWithStudentsCountResponseDto actual = groupService.addGroup(groupCreateRequestDto);

        assertEquals(group.getId(), actual.id());
        assertEquals(group.getNumber(), actual.number());
    }

    @Test
    void getGroups_returnsPagedResult() {
        Page<GroupWithStudentsCountResponseDto> page = new PageImpl<>(List.of(dto));
        when(groupRepository.findAllWithStudentCount(PageRequest.of(0, 10))).thenReturn(page);

        Page<GroupWithStudentsCountResponseDto> actual = groupService.getGroups(PageRequest.of(0, 10));

        assertEquals(1, actual.getTotalElements());
        assertEquals(dto, actual.getContent().get(0));
    }

    @Test
    void groupExists_returnsTrueWhenGroupFound() {
        when(groupRepository.existsById(1L)).thenReturn(true);

        assertEquals(true, groupService.groupExists(1L));
    }
}
