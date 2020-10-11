package Farm;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DBBean {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int num = 0;
	private static DBBean instance = new DBBean();

	public static DBBean getInstance() { // 생성자를 private로 선언하여 다른 클래스에서 해당 클래스의 인스턴스를 new로 생성하지 못하게 하고, getinstance()함수를
											// 통해서 인스턴스를 갖도록 한다.
		return instance;
	}

	DBBean() {

	};
	// 데이터베이스 연결
	public Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "system";
			String pw = "1234";
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 적재 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("적재 실패");
		}
		return conn;

	}
	
	
	//입력된 기록을 디비에서 긁어(쿼리문으로 정렬까지 다 하고)와 프레임에 뿌려줌  
	public ArrayList selectLog() {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs;
	      Recordbean rbean;
	      ArrayList<Recordbean> arr = new ArrayList<Recordbean>();
	      try {
	         conn = getConnection();
	         //users테이블에 money를 정렬해서 정렬한 가상의 테이블의 행 번호가 5까지인 것까지 보여준다.
	         String sql = "select * from (select * from users order by money DESC) where rownum <= 5";
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         while (rs.next()) {
	            rbean = new Recordbean();
	            rbean.setUser_id(rs.getString("user_id"));
	            rbean.setUser_berry(rs.getInt("user_berry"));
	            arr.add(rbean);
	         }
	      } catch (SQLException e) {
	         JOptionPane.showMessageDialog(null, "실패");
	      } finally {
	         if (pstmt != null) {
	            try {
	               pstmt.close();
	            } catch (SQLException e) {
	            }
	         }
	         if (conn != null) {
	            try {
	               conn.close();
	            } catch (SQLException e) {
	            }
	         }
	      }
	      return arr;
	   }
	
	 // 유저 체크
	public int select(String id, String pw) {
		try {
			conn = getConnection();
			String sql = "select * from users where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String dbpw = rs.getString("user_pw");
				if (pw.equals(dbpw)) { // 로그인 성공
					num = 1; // 아이디,비번 성공
				} else {
					num = 0; // 비번틀림
				}
			} else {
				num = -1; // 아이디 존재x
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return num;

	}

	// 게임종료후 기록을 입력하는 부분
	public void updateLog(String id, int berry, int melon, int pum, int potato, int lettuce, int corn) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update users set berry = (select berry from users where id = ?)+? where id = ?;" +
			         "update users set melon = (select melon from users where id = ?)+? where id = ?;"+
			         "update users set pum = (select pum from users where id = ?)+? where id = ?;" +
			         "update users set potato = (select potato from users where id = ?)+? where id = ?;"+
			         "update users set lettuce = (select lettuce from users where id = ?)+? where id = ?;"+
			         "update users set corn = (select corn from users where id = ?)+? where id = ?;";;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, berry);
			pstmt.setInt(3, melon);
			pstmt.setInt(4, pum);
			pstmt.setInt(5, potato);
			pstmt.setInt(6, lettuce);
			pstmt.setInt(7, corn);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "기록을 완료하였습니다.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "기록에 실패하였습니다.");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	// 회원가입
	public void adduser(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			int check = memberCheck(id, passwd);

			if (check == 1) {
				conn = getConnection();
				String sql = "insert into users values(?,?,0,0,0,0,0,0)"; // 아이디,비밀번호,과일들~
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, passwd);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "새 계정이 생성되었습니다.");
			} else {
				JOptionPane.showMessageDialog(null, "아이디가 존재합니다.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "회원가입 실패");
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}
	
	// 중복 체크
	public int memberCheck(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select * from users where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next() == true) {
				return 0; // 아이디 있음

			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "회원가입 실패2");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return 1; // 아이디 없음
	}
}
