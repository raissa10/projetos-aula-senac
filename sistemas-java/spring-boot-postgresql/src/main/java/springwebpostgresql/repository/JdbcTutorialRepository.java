package springwebpostgresql.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import springwebpostgresql.model.Tutorial;

@Repository
public class JdbcTutorialRepository implements TutorialRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int save(Tutorial tutorial) {
    return jdbcTemplate.update("INSERT INTO tutorial (title, description, published) VALUES(?,?,?)",
        new Object[] { tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished() });
  }

  @Override
  public int update(Tutorial tutorial) {
    return jdbcTemplate.update("UPDATE tutorial SET title=?, description=?, published=? WHERE id=?",
        new Object[] { tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId() });
  }

  @Override
  public Tutorial findById(Long id) {
    try {
      Tutorial tutorial = jdbcTemplate.queryForObject("SELECT * FROM tutorial WHERE id=?",
          BeanPropertyRowMapper.newInstance(Tutorial.class), id);

      return tutorial;
    } catch (IncorrectResultSizeDataAccessException e) {
      return null;
    }
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update("DELETE FROM tutorial WHERE id=?", id);
  }

  @Override
  public List<Tutorial> findAll() {
    return jdbcTemplate.query("SELECT * from tutorial", BeanPropertyRowMapper.newInstance(Tutorial.class));
  }

  @Override
  public List<Tutorial> findByPublished(boolean published) {
    return jdbcTemplate.query("SELECT * from tutorial WHERE published=?",
        BeanPropertyRowMapper.newInstance(Tutorial.class), published);
  }

  @Override
  public List<Tutorial> findByTitleContaining(String title) {
    String q = "SELECT * from tutorial WHERE title ILIKE '%" + title + "%'";

    return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Tutorial.class));
  }

  @Override
  public int deleteAll() {
    return jdbcTemplate.update("DELETE from tutorial");
  }
}
