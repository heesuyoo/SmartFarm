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

	public static DBBean getInstance() { // �����ڸ� private�� �����Ͽ� �ٸ� Ŭ�������� �ش� Ŭ������ �ν��Ͻ��� new�� �������� ���ϰ� �ϰ�, getinstance()�Լ���
											// ���ؼ� �ν��Ͻ��� ������ �Ѵ�.
		return instance;
	}

	DBBean() {

	};
	// �����ͺ��̽� ����
	public Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "system";
			String pw = "1234";
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("����̹� ���� ����");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("���� ����");
		}
		return conn;

	}
	
	
	//�Էµ� ����� ��񿡼� �ܾ�(���������� ���ı��� �� �ϰ�)�� �����ӿ� �ѷ���  
	public ArrayList selectLog() {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs;
	      Recordbean rbean;
	      ArrayList<Recordbean> arr = new ArrayList<Recordbean>();
	      try {
	         conn = getConnection();
	         //users���̺� money�� �����ؼ� ������ ������ ���̺��� �� ��ȣ�� 5������ �ͱ��� �����ش�.
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
	         JOptionPane.showMessageDialog(null, "����");
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
	
	 // ���� üũ
	public int select(String id, String pw) {
		try {
			conn = getConnection();
			String sql = "select * from users where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String dbpw = rs.getString("user_pw");
				if (pw.equals(dbpw)) { // �α��� ����
					num = 1; // ���̵�,��� ����
				} else {
					num = 0; // ���Ʋ��
				}
			} else {
				num = -1; // ���̵� ����x
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

	// ���������� ����� �Է��ϴ� �κ�
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
			JOptionPane.showMessageDialog(null, "����� �Ϸ��Ͽ����ϴ�.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "��Ͽ� �����Ͽ����ϴ�.");
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

	// ȸ������
	public void adduser(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			int check = memberCheck(id, passwd);

			if (check == 1) {
				conn = getConnection();
				String sql = "insert into users values(?,?,0,0,0,0,0,0)"; // ���̵�,��й�ȣ,���ϵ�~
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, passwd);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "�� ������ �����Ǿ����ϴ�.");
			} else {
				JOptionPane.showMessageDialog(null, "���̵� �����մϴ�.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ȸ������ ����");
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
	
	// �ߺ� üũ
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
				return 0; // ���̵� ����

			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ȸ������ ����2");
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
		return 1; // ���̵� ����
	}
}
