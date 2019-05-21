package com.augmentum.mediacloud.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "user")
@Table(name = "t_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @org.springframework.data.annotation.Id
    private ObjectId oid;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_user_id_seq")
    @SequenceGenerator(name = "t_user_id_seq", sequenceName = "t_user_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    @Column(nullable = false)
    private String nativePlace;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private Date createDate;

    @Column(nullable = false)
    private Date updateDate;

    public static final String O_ID = "oid";

    public static final String USER_NAME = "userName";

    public static final String DELETED = "deleted";

    public static final String BIRTH_DATE = "birthDate";

    public static final String GENDER = "gender";

    public static final String NATIVE_PLACE = "nativePlace";

    public static final String NATIONALITY = "nationality";

    public static final String POSITION = "position";

    public static final String CREATE_DATE = "createDate";

    public static final String UPDATE_DATE = "updateDate";

    public User() {
        super();
    }

    public User(String userName, String gender, Date birthDate, String nativePlace, String nationality, String position,
            boolean deleted, Date createDate, Date updateDate) {
        super();
        this.userName = userName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.nativePlace = nativePlace;
        this.nationality = nationality;
        this.position = position;
        this.deleted = deleted;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public ObjectId getOid() {
        return oid;
    }

    public void setOid(ObjectId oid) {
        this.oid = oid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", gender=" + gender + ", birthDate=" + birthDate + ", nativePlace="
                + nativePlace + ", nationality=" + nationality + ", position=" + position + ", deleted=" + deleted
                + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
    }
}
