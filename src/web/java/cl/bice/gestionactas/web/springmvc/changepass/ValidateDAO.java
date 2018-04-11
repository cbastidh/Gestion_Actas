package cl.bice.gestionactas.web.springmvc.changepass;

public class ValidateDAO {
	private String CAMPO;
	private int ESTADO;
	private String DESCRIPCION;
	
	public ValidateDAO(){
		this.CAMPO = "";
		this.ESTADO = -1;
		this.DESCRIPCION = "";
	}

	public String getCAMPO() {
		return CAMPO;
	}
	public void setCAMPO(String cAMPO) {
		CAMPO = cAMPO;
	}
	public int getESTADO() {
		return ESTADO;
	}
	public void setESTADO(int eSTADO) {
		ESTADO = eSTADO;
	}
	public String getDESCRIPCION() {
		return DESCRIPCION;
	}
	public void setDESCRIPCION(String dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}

}
