package com.testcode.study.testCodeStudy.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 학습 테스트 : 라이브러리, 프레임워크 등 다양한 기능들을 사용 전 테스트 코드를 작성하여 공부할 수 있다.
 */
public class GuavaLearningTest {

    @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
    @Test
    void partitionLearningTest1() {
        // given
        List<Integer> integers = List.of( 1,2,3,4,5,6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 3);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1,2,3),List.of(4,5,6)
                ));
    }

    @DisplayName("딱 떨어지는 숫자가 아니라면 마지막엔 파티셔닝 개수가 아닌 나머지 개수가 들어간다.")
    @Test
    void partitionLearningTest2() {
        // given
        List<Integer> integers = List.of( 1,2,3,4,5,6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 4);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1,2,3,4),List.of(5,6)
                ));
    }

    @DisplayName("멀티맵 기능 확인")
    @Test
    void multimapLearningTest1() {
        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        // when
        Collection<String> strings = multimap.get("커피");

        // then
        assertThat(strings).hasSize(3)
                .isEqualTo(List.of(
                        "아메리카노", "카페라떼", "카푸치노"
                ));
    }    
    
    @DisplayName("멀티맵 삭제 기능 확인")
    @TestFactory
    Collection<DynamicTest> multimapLearningTest2() {
        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        return List.of(

                DynamicTest.dynamicTest("1개 value를 삭제한다.", () -> {
                    // when
                    multimap.remove("커피", "카푸치노");
                    // then
                    Collection<String> result = multimap.get("커피");
                    assertThat(result).hasSize(2)
                            .isEqualTo(List.of("아메리카노", "카페라떼"));
                }),

                DynamicTest.dynamicTest("1개 key를 삭제한다.", () -> {
                    // when
                    multimap.removeAll("커피");
                    // then
                    Collection<String> result = multimap.get("커피");
                    assertThat(result).isEmpty();
                })

        );
    }
}
