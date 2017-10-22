package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	private int id;
	private String nik;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private int jenis_kelamin;
	private int is_wni;
	private int id_keluarga;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private int is_wafat;
	private String nik_current;
	
	private String wargaNegara;
	private String statusWafat;
	
	private String alamat;
	private String RT;
	private String RW;
	private int is_tidak_berlaku;
	
	private String kode_kelurahan;
	private String nama_kelurahan;
	private String kode_pos;
	
	private String kode_kecamatan;
	private String nama_kecamatan;
	
	private String kode_kota;
	private String nama_kota;
}