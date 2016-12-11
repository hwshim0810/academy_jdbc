package west.mem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class IdPassSearchDialogue extends JDialog implements ActionListener{
	private static final long serialVersionUID = 8044637289072486932L;
	
	JLabel l_idSch, l_lisence, l_lisence1,l_pwdSch, l_id, l_empty;
	JTextField jt_lisence, jt_id,jt_lisence1;
	JButton b_idSch,b_lisSch;
	MemDAO dao = new MemDAO();
	
	public IdPassSearchDialogue(){
		setLayout(null);
		l_idSch = new JLabel("아이디 찾기");
		l_lisence = new JLabel("면허번호");
		l_lisence1 = new JLabel("면허번호");
		l_pwdSch = new JLabel("비밀번호 찾기");
		l_id = new JLabel("아이디");
		l_empty = new JLabel("");
		jt_lisence = new JTextField(5);
		jt_id = new JTextField(10);
		jt_lisence1 = new JTextField(10);
		b_idSch= new JButton("찾기");
		b_lisSch= new JButton("찾기");
		
		l_idSch.setBounds(40, 20, 200, 20);		
		l_lisence.setBounds(80, 55, 100, 20);
		jt_lisence.setBounds(150, 55, 250, 20);
		b_idSch.setBounds(300, 90, 100, 30);
		
		
		l_pwdSch.setBounds(40, 140, 100, 20);
		l_id.setBounds(80, 170, 100 , 20);
		jt_id.setBounds(150, 170, 250, 20);
		l_lisence1.setBounds(80, 200, 100, 20);
		jt_lisence1.setBounds(150, 200,250,20);
		b_lisSch.setBounds(300, 230, 100, 30);
		
		add(l_idSch);
		add(l_lisence);
		add(jt_lisence);		
		add(b_idSch);	
		
		add(l_pwdSch);
		add(l_id);
		add(jt_id);
		add(l_lisence1);
		add(jt_lisence1);
		add(b_lisSch);
		
		setTitle("아이디/비밀번호 찾기");
		setBounds(650,350,440,320);	
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		b_idSch.addActionListener(this);
		b_lisSch.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b_idSch) {
			dao.SearchCheck(jt_lisence.getText().trim());
			
		}else if(e.getSource()==b_lisSch){
			dao.SearchCheck(jt_id.getText().trim() ,jt_lisence.getText().trim());
		}
		
	}
}
