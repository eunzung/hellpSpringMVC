package kr.ac.hansung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.model.Offer;

@Repository
public class OfferDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int getRowCount() {
		String sqlStatement = "select count(*) from offers";
		return jdbcTemplate.queryForObject(sqlStatement, Integer.class);
	}
	
	public Offer getOffer(String name) {
		String sqlStatement = "select * from offers where name=?";
		return jdbcTemplate.queryForObject(sqlStatement, new Object[] {name}, 
				new RowMapper<Offer>(){
					@Override
					public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
						Offer offer = new Offer();

						offer.setYear(rs.getInt("year"));
						offer.setTerm(rs.getInt("term"));
						offer.setSubject(rs.getString("subject"));
						offer.setCode(rs.getString("code"));
						offer.setDivision(rs.getString("division"));
						offer.setGrade(rs.getInt("grade"));
						
						return offer;
					}
			});
	}
	
	public List<Offer> getLinks(int year, int term){
		
		String sqlStatement = "select subject, code, division from offers where year=? and term=?";
		
		return jdbcTemplate.query(sqlStatement,	new Object[] {year, term}, new RowMapper<Offer>(){
					@Override
					public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
						Offer offer = new Offer();
						
						offer.setSubject(rs.getString("subject"));
						offer.setCode(rs.getString("code"));
						offer.setDivision(rs.getString("division"));
						
						return offer;
					}
			});
	}
	
		public List<Offer> getyears(){
			String sqlStatement = "select year, term, sum(grade) from offers group by year, term" ;
			
			
			return jdbcTemplate.query(sqlStatement,	new RowMapper<Offer>(){
						@Override
						public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
							Offer offer = new Offer();
							
							offer.setYear(rs.getInt("year"));
							offer.setTerm(rs.getInt("term"));
							offer.setGrade(rs.getInt("sum(grade)"));
							
							return offer;
						}
				});
		}
	
		public List<Offer> getOffers() {
			String sqlStatement = "select * from offers";
			
			return jdbcTemplate.query(sqlStatement, 
					new RowMapper<Offer>(){
						@Override
						public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
							//rs = record   
							Offer offer = new Offer();
							
							offer.setYear(rs.getInt("year"));
							offer.setTerm(rs.getInt("term"));
							offer.setSubject(rs.getString("subject"));
							offer.setCode(rs.getString("code"));
							offer.setDivision(rs.getString("division"));
							offer.setGrade(rs.getInt("grade"));
							
							return offer;
						}
				});
		}
		
		
		public List<Offer> getNexts() {
			String sqlStatement = "select * from offers where year=? and term=?";
			
			return jdbcTemplate.query(sqlStatement, new Object[] {2019, 1},
					new RowMapper<Offer>(){
						@Override
						public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
							Offer offer = new Offer();
							
							offer.setYear(rs.getInt("year"));
							offer.setTerm(rs.getInt("term"));
							offer.setSubject(rs.getString("subject"));
							offer.setCode(rs.getString("code"));
							offer.setDivision(rs.getString("division"));
							offer.setGrade(rs.getInt("grade"));
							
							return offer;
						}
				});
		}
		
		
		
		public boolean insert(Offer offer) {
			int year = offer.getYear();
			int term = offer.getTerm();
			String subject = offer.getSubject();
			String code = offer.getCode();
			String division = offer.getDivision();
			int grade = offer.getGrade();
			
			
			String sqlStatement = "insert into offers (year, term, subject, code, division, grade) values (?, ?, ?, ?, ?, ?)";
						
			return (jdbcTemplate.update(sqlStatement, new Object[]{year, term, subject, code, division, grade}) == 1);
		}
		
		public boolean update(Offer offer) {
			int year = offer.getYear();
			int term = offer.getTerm();
			String subject = offer.getSubject();
			String code = offer.getCode();
			String division = offer.getDivision();
			int grade = offer.getGrade();
			String sqlStatement = "update offers set year=?, term=?, subject=?, code=?, division=?, grade=?";
			
			return (jdbcTemplate.update(sqlStatement, new Object[]{year, term, subject, code, division, grade}) == 1);
		}
		
		public boolean delete(int id) {
			String sqlStatement = "delete from offers where id=?";
			return (jdbcTemplate.update(sqlStatement, new Object[]{id}) == 1);
		}
	
	
}
