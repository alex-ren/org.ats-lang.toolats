package org.ats_lang.toolats.syntax.level2;


import org.ats_lang.toolats.syntax.I0de;

public interface D2ecl_node {

	public class D2Cnone implements D2ecl_node {
		public D2Cnone() {	
		}
	}
	
	static class D2Clist implements D2ecl_node {
		D2ecl_node[] m_mem0;
		
		public D2Clist(D2ecl_node[] mem0) {
			m_mem0 = mem0;
		}
	}
	
	static class D2Csymintr implements D2ecl_node {
		I0de[] m_mem0;
		
		public D2Csymintr(I0de[] mem0) {
			m_mem0 = mem0;
		}
	}
	
}
