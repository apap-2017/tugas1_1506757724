package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private int id;
	private String nomor_kk;
	private String alamat;
	private String RT;
	private String RW;
	private String id_kelurahan;
	private int is_tidak_berlaku;
	
	private String wargaNegara;
	
	private String nik;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private int jenis_kelamin;
	private int is_wni;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private int is_wafat;
	
	private String kode_kelurahan;
	private String nama_kelurahan;
	private String kode_pos;
	
	private String id_kecamatan;
	private String kode_kecamatan;
	private String nama_kecamatan;
	
	private String id_kota;
	private String kode_kota;
	private String nama_kota;
	
	private List<PendudukModel> keluargaPenduduk;
}