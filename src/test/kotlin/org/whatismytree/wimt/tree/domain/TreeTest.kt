package org.whatismytree.wimt.tree.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.whatismytree.wimt.tree.entity.Tree
import java.time.LocalDate

class TreeTest {
    @Nested
    inner class Of {

        @Test
        @DisplayName("트리를 생성한다")
        fun createTree() {
            // given
            val userId = 1L
            val name = "명동 신세계 트리"
            val lat = 37.56052658245116.toFloat()
            val lng = 126.98065056929003.toFloat()
            val addressType = Tree.AddressType.ROAD
            val roadAddress = "서울 중구 퇴계로 77"
            val detailAddress = "명동 신세계 백화점"
            val space = Tree.Space.EXTERNAL
            val exhibitionStartDate = LocalDate.of(2023,12,1)
            val exhibitionEndDate = LocalDate.of(2023,12,31)
            val businessDays = "월,화,수,목,금,토,일"
            val isPet = true
            val extraInfo = "명동 신셰계 백화점 크리스마스 트리"

            // when
            val tree = Tree(
                userId = userId,
                name = name,
                imageUrl = null,
                lat = lat,
                lng = lng,
                addressType = addressType,
                roadAddress = roadAddress,
                detailAddress = detailAddress,
                space = space,
                exhibitionStartDate = exhibitionStartDate,
                exhibitionEndDate = exhibitionEndDate,
                businessDays = businessDays,
                isPet = isPet,
                extraInfo = extraInfo,
            )

            // then
            Assertions.assertThat(tree.name).isEqualTo(name)
            Assertions.assertThat(tree.lat).isEqualTo(lat)
            Assertions.assertThat(tree.lng).isEqualTo(lng)
            Assertions.assertThat(tree.addressType).isEqualTo(addressType)
            Assertions.assertThat(tree.roadAddress).isEqualTo(roadAddress)
            Assertions.assertThat(tree.detailAddress).isEqualTo(detailAddress)
            Assertions.assertThat(tree.space).isEqualTo(space)
            Assertions.assertThat(tree.exhibitionStartDate).isEqualTo(exhibitionStartDate)
            Assertions.assertThat(tree.exhibitionEndDate).isEqualTo(exhibitionEndDate)
            Assertions.assertThat(tree.businessDays).isEqualTo(businessDays)
            Assertions.assertThat(tree.isPet).isEqualTo(isPet)
            Assertions.assertThat(tree.extraInfo).isEqualTo(extraInfo)
        }
    }
}
