package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // em은 스레드간에 공유하면 안됨. 사용하고 버려야 한다.
        EntityManager em = emf.createEntityManager();

        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member)

            //수정
//            Member findMember = em.find(Member.class, 1L);
//            em.remove(findMember); //삭제
//            findMember.setName("HelloJPA");

            // JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
            // JPQL은 엔티티 객체를 대상으로 쿼리
            // SQL은 데이터베이스 테이블을 대상으로 쿼리
            List<Member> result = em.createQuery("select m from Member", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for(Member member:result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
