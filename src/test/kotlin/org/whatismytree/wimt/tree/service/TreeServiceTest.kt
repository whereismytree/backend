package org.whatismytree.wimt.tree.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.whatismytree.wimt.tree.controller.dto.CreateTreeDto
import org.whatismytree.wimt.tree.entity.Tree
import java.time.LocalDate

@SpringBootTest
class TreeServiceTest @Autowired constructor(
    private val treeService: TreeService,
) {

    @Nested
    inner class CreateTree {

        @Test
        @DisplayName("트리를 생성한다")
        fun createTree() {
            // given
            val name = "명동 신세계 트리"
            val lat = 37.56052658245116.toFloat()
            val lng = 126.98065056929003.toFloat()
            val addressType = Tree.AddressType.ROAD
            val roadAddress = "서울 중구 퇴계로 77"
            val detailAddress = "명동 신세계 백화점"
            val space = Tree.Space.EXTERNAL
            val exhibitionStartDate = LocalDate.of(2023, 12, 1)
            val exhibitionEndDate = LocalDate.of(2023, 12, 31)
            val businessDays = "월,화,수,목,금,토,일"
            val isPet = true
            val title = "명동 신셰계 백화점 크리스마스 트리"
            val extraInfo = "명동 신셰계 백화점 크리스마스 트리"

            val req = CreateTreeDto.Req(
                name = name,
                imageUrl = null,
                lat = lat,
                lng = lng,
                addressType = addressType.name,
                roadAddress = roadAddress,
                streetAddress = null,
                detailAddress = detailAddress,
                exhibitionStartDate = exhibitionStartDate,
                exhibitionEndDate = exhibitionEndDate,
                spaceType = space.name,
                businessDays = businessDays,
                isPet = isPet,
                title = title,
                extraInfo = extraInfo,
            )

            // when
            val tree = treeService.createTree(req, 1L)

            // then
            Assertions.assertThat(tree).isNotNull()
        }

        @Test
        @DisplayName("트리를 조회한다")
        fun findTree() {
            // given
            val id = 1L

            // when
            val tree = treeService.findTree(id, 1L)

            // then
            Assertions.assertThat(tree).isNotNull()
        }
    }
}
