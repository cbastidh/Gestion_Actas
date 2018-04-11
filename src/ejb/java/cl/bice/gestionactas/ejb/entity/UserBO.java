package cl.bice.gestionactas.ejb.entity;

import cl.bice.gestionactas.ejb.dao.UserDAO;
import cl.bice.gestionactas.ejb.provider.SessionProvider;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.lock.Lock;
import cl.neoptera.ejb.dao.lock.LockListener;

import javax.persistence.*;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Entity
@Table(name = "usr_usuario")
@Lock(id="id", semaphore = Semaphore.class, precedence = 1)
@EntityListeners({LockListener.class})
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class UserBO implements Identified<Long> {

    @Id
    @GeneratedValue(generator = "IdGen", strategy = GenerationType.SEQUENCE)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "nom_nombre")
    private String name;

    @Column(name = "rut_usuario")
    private String rut;

    @Column(name = "nom_loginad")
    private String loginAD;

    @Column(name = "usr_estado")
    private Boolean estado;

    protected UserBO() {
    }

    public UserBO(String loginAD, String name) {
        this.name = name;
        this.loginAD = loginAD;
        this.estado = true;
        SessionProvider provider = SessionProvider.instance();
        UserDAO userDAO = provider.getBean(UserDAO.class);
        userDAO.insert(this);
    }

    public UserBO(UserVO userVO) {
        this.id = userVO.getId();
        this.name = userVO.getName();
        this.loginAD = userVO.getLoginAD();
        this.rut = userVO.getRut();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginAD() {
        return loginAD;
    }

    public void setLoginAD(String loginAD) {
        this.loginAD = loginAD;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public UserVO toVO() {
        return new UserVO(id, name, rut, loginAD);
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void update(UserVO user) {
        name = user.getName();
        loginAD = user.getLoginAD();
        rut = user.getRut();
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rut='" + rut + '\'' +
                ", loginAD='" + loginAD + '\'' +
                '}';
    }
}
