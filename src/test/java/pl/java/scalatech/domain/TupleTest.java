package pl.java.scalatech.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.TestSelectorConfig;
import pl.java.scalatech.domain.poll.Simple;
import pl.java.scalatech.repository.poll.SimpleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSelectorConfig.class)
@ActiveProfiles("test")
@Slf4j
public class TupleTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SimpleRepository simpleRepository;
    
    // abstract entity bo id jest w abstractEntity a nie w Simple
    public <ENTITY> Page<ENTITY> genericSolution(Class<ENTITY> entity, Map<String, SingularAttribute<? extends AbstractEntity, ?>> paths,
            Function<Tuple, ENTITY> mapToEntity, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        final Root<ENTITY> from = query.from(entity);
        
                
        List<Path<?>> pathsInner= paths.entrySet().stream().map((t) -> (Path<?>) from.get(t.getKey()).alias(t.getValue().getName())).collect(Collectors.toList());        

        Path[] pathsArray = new Path[pathsInner.size()];
        query.select(builder.tuple(pathsInner.toArray(pathsArray)));

        TypedQuery<Tuple> result = entityManager.createQuery(query);
        if (pageable != null) {
            result.setFirstResult(pageable.getOffset());
            result.setMaxResults(pageable.getPageSize());
        }
        final List<Tuple> list = entityManager.createQuery(query).getResultList();
        return new PageImpl<>(list.stream().map(mapToEntity).collect(Collectors.toList()), pageable, 0);   // total zamiast 0

    }

    @Test
    public void tupleTest() {
        Simple s = Simple.builder().name("przodownik").createDate(LocalDateTime.now()).build();
        Simple loaded = simpleRepository.save(s);
        Map<String, SingularAttribute<? extends AbstractEntity, ?>> path = Maps.newHashMap();
        path.put(Skill_.id.getName(), Skill_.id);
        path.put(Skill_.name.getName(), Skill_.name);        
        
        //path.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getKey));                                            
        Function<Tuple, Simple> tupleToSimple = t -> Simple.builder().id((Long) t.get(Skill_.id.getName())).name((String) t.get(Skill_.name.getName())).build();
        Page<Simple> result = genericSolution(Simple.class, path, tupleToSimple, new PageRequest(0, 20));
        log.info("{}", result.getContent());

        Pageable pageable = new PageRequest(0, 10);
        final String id = "";
        Specification<Tuple> spec = (root, query, cb) -> query.getRestriction();
        // simpleRepository.findAll(spec);
        
        Supplier<Simple> sup = Simple::new;
       
        
    }
}

/*
 * Path<Long> idPath = from.get("id");
 * Path<String> namePath = from.get("name");
 */
