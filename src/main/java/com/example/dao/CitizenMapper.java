package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;


@Mapper
public interface CitizenMapper {
	@Select("SELECT * " +
			"FROM penduduk P, keluarga K, kelurahan KEL, kecamatan KEC, kota KOT " +
			"WHERE nik = #{nik} AND P.id_keluarga=K.id AND K.id_kelurahan=KEL.id AND KEL.id_kecamatan=KEC.id AND KEC.id_kota=KOT.id")
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	
	@Select("SELECT nomor_kk, alamat, RT, RW, nama_kelurahan, nama_kecamatan, nama_kota " +
			"FROM keluarga K, kelurahan KEL, kecamatan KEC, kota KOT " +
			"WHERE nomor_kk = #{nomor_kk} AND K.id_kelurahan=KEL.id AND KEL.id_kecamatan=KEC.id AND KEC.id_kota=KOT.id")
	@Results(value = {
	    	@Result(property="nomor_kk", column="nomor_kk"),
	       	@Result(property="alamat", column="alamat"),
	        @Result(property="RT", column="RT"),
	        @Result(property="RW", column="RW"),
	        @Result(property="nama_kelurahan", column="nama_kelurahan"),
	        @Result(property="nama_kecamatan", column="nama_kecamatan"),
	        @Result(property="nama_kota", column="nama_kota"),
	       	@Result(property="keluargaPenduduk", column="nomor_kk",
	       			javaType = List.class,
	       			many=@Many(select="selectKeluargaPenduduk"))
	    })
	KeluargaModel selectKeluarga (@Param("nomor_kk") String nomor_kk);
	
	@Select("SELECT nama, nik, jenis_kelamin, tempat_lahir, tanggal_lahir, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, is_wni " +
			"FROM penduduk P, keluarga K " +
			"WHERE nomor_kk = #{nomor_kk} AND K.id=P.id_keluarga")
	List<PendudukModel> selectKeluargaPenduduk(@Param("nomor_kk") String nomor_kk);

	
	@Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) " +
			"VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(PendudukModel citizen);

	@Select("SELECT * FROM penduduk")
	List<PendudukModel> selectAllPenduduk();

	@Select("SELECT kode_kecamatan " +
			"FROM keluarga K, kelurahan KEL, kecamatan KEC, kota KOT " +
			"WHERE K.id = #{id} AND K.id_kelurahan=KEL.id AND KEL.id_kecamatan=KEC.id AND KEC.id_kota=KOT.id")
	KeluargaModel selectKeluargaById(@Param("id") int id);

	@Select("SELECT nama_kecamatan " +
			"FROM keluarga K, kota KOT " +
			"WHERE .KOT.id_kota=#{id_kota} AND KOT.id=K.id_kota")
	List<KeluargaModel> dropDownKecamatan(String id_kota);

	@Select("SELECT * FROM KOTA")
	List<KotaModel> selectAllKota();

	@Select("SELECT * FROM KECAMATAN")
	List<KecamatanModel> selectAllKecamatan();
	
	@Select("SELECT * FROM KELURAHAN")
	List<KelurahanModel> selectAllKelurahan();
	
	@Select("SELECT * FROM keluarga")
	List<KeluargaModel> selectAllKeluarga();

	@Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void addKeluarga(KeluargaModel keluarga);

	@Update("UPDATE penduduk SET nik = #{nik}, nama=#{nama}, jenis_kelamin=#{jenis_kelamin}, tempat_lahir=#{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, golongan_darah=#{golongan_darah}, agama=#{agama}, status_perkawinan=#{status_perkawinan}, status_dalam_keluarga=#{status_perkawinan}, pekerjaan=#{pekerjaan}, is_wni=#{is_wni}, is_wafat=#{is_wafat}, id_keluarga=#{id_keluarga} "
			+ "WHERE nik = #{nik_current}")
	void updatePenduduk(PendudukModel citizen);
	
}