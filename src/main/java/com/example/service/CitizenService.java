package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface CitizenService {
	PendudukModel selectPenduduk(String nik);
	
	KeluargaModel selectKeluarga(String nkk);
	KeluargaModel selectKeluargaById(int id);

	void addPenduduk(PendudukModel citizen);
	
	List<PendudukModel> selectAllPenduduk();
	
	List<KeluargaModel> selectAllKeluarga();

	List<KotaModel> selectAllKota();

	List<KecamatanModel> selectAllKecamatan();

	List<KelurahanModel> selectAllKelurahan();

	void addKeluarga(KeluargaModel keluarga);

	void updatePenduduk(PendudukModel citizen);
	
}
