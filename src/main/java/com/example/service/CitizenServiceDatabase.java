package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CitizenMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CitizenServiceDatabase implements CitizenService {
	
	@Autowired
	private CitizenMapper citizenMapper;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		// TODO Auto-generated method stub
		log.info("select penduduk with nik {}", nik);
		return citizenMapper.selectPenduduk(nik);
	}

	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		// TODO Auto-generated method stub
		log.info("select keluarga with nkk {}", nkk);
		return citizenMapper.selectKeluarga(nkk);
	}

	@Override
	public void addPenduduk(PendudukModel citizen) {
		// TODO Auto-generated method stub
		citizenMapper.addPenduduk(citizen);	
	}

	@Override
	public List<PendudukModel> selectAllPenduduk() {
		// TODO Auto-generated method stub
		return citizenMapper.selectAllPenduduk();
	}

	@Override
	public KeluargaModel selectKeluargaById(int id) {
		// TODO Auto-generated method stub
		return citizenMapper.selectKeluargaById(id);
	}

	@Override
	public List<KotaModel> selectAllKota() {
		// TODO Auto-generated method stub
		return citizenMapper.selectAllKota();
	}

	@Override
	public List<KecamatanModel> selectAllKecamatan() {
		// TODO Auto-generated method stub
		return citizenMapper.selectAllKecamatan();
	}

	@Override
	public List<KelurahanModel> selectAllKelurahan() {
		// TODO Auto-generated method stub
		return citizenMapper.selectAllKelurahan();
	}

	@Override
	public List<KeluargaModel> selectAllKeluarga() {
		// TODO Auto-generated method stub
		return citizenMapper.selectAllKeluarga();
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		citizenMapper.addKeluarga(keluarga);
	}

	@Override
	public void updatePenduduk(PendudukModel citizen) {
		// TODO Auto-generated method stub
		citizenMapper.updatePenduduk(citizen);
	}
}
