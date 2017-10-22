package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;
import com.example.service.CitizenService;



@Controller
public class CitizenController {
	@Autowired
	CitizenService citizenDAO;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/penduduk")
	public String checkCitizen(Model model, @RequestParam(value = "nik", required = false) String nik)
	{
		PendudukModel penduduk = citizenDAO.selectPenduduk(nik);
		if(penduduk.getIs_wni() == 1) {
			penduduk.setWargaNegara("WNI");
			if(penduduk.getIs_wafat() == 0) {
				penduduk.setStatusWafat("Hidup");
			}
			else {
				penduduk.setStatusWafat("Wafat");
			}
		}
		else {
			penduduk.setWargaNegara("WNA");
			if(penduduk.getIs_wafat() == 0) {
				penduduk.setStatusWafat("Hidup");
			}
			else {
				penduduk.setStatusWafat("Wafat");
			}
		}
		System.out.println(penduduk);
		model.addAttribute("penduduk", penduduk);
		return "viewPenduduk";
	}
	
	@RequestMapping("/keluarga")
	public String checkFamily(Model model, @RequestParam(value = "nkk", required = false) String nkk)
	{
		KeluargaModel keluarga = citizenDAO.selectKeluarga(nkk);
		for(int i = 0; i < keluarga.getKeluargaPenduduk().size(); i++) {
			if(keluarga.getKeluargaPenduduk().get(i).getIs_wni() == 1){
				keluarga.getKeluargaPenduduk().get(i).setWargaNegara("WNI");
			}else {
				keluarga.getKeluargaPenduduk().get(i).setWargaNegara("WNA");
			}
		}
		model.addAttribute("keluarga", keluarga);
		return "viewKeluarga";
	}
	
	@RequestMapping("/penduduk/tambah")
	public String addCitizen(Model model) {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk", penduduk);
		return "formAddPenduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String addCitizenSubmit(Model model, PendudukModel penduduk)
	{	
		KeluargaModel keluarga = citizenDAO.selectKeluargaById(penduduk.getId_keluarga());
		
		String kode_kecamatan = keluarga.getKode_kecamatan();
		kode_kecamatan = kode_kecamatan.substring(0, kode_kecamatan.length()-1);
		
		String nik = "";
		String tanggal_lahir = penduduk.getTanggal_lahir();
		String yy_tanggal_lahir = tanggal_lahir.substring(2, 4);
		String mm_tanggal_lahir = tanggal_lahir.substring(5, 7);
		String dd_tanggal_lahir = tanggal_lahir.substring(8, 10);
		
		if (penduduk.getJenis_kelamin() == 1) {
			int int_dd_tanggal_lahir = Integer.parseInt(dd_tanggal_lahir);
			dd_tanggal_lahir = "" + (int_dd_tanggal_lahir + 40);
		}
		nik = kode_kecamatan + dd_tanggal_lahir + mm_tanggal_lahir + yy_tanggal_lahir;
		
		int index = 1;
		String cekKesamaan = "000" + index;
		PendudukModel cek_kesamaan_nik = citizenDAO.selectPenduduk(nik + cekKesamaan);
		while (cek_kesamaan_nik != null) {
			index++;
			if(index <= 9) {
				cekKesamaan = "000" + index;
			} else if (index > 9 && index <= 99) {
				cekKesamaan = "00" + index;
			} else if (index > 99 && index <= 999) {
				cekKesamaan = "0" + index;
			} else {
				cekKesamaan = "" + index;
			}
			cek_kesamaan_nik = citizenDAO.selectPenduduk(nik + cekKesamaan);
		}
		List<PendudukModel> total_penduduk = citizenDAO.selectAllPenduduk();
		penduduk.setId(total_penduduk.size()+1);
		
		
		nik = nik + cekKesamaan;
		penduduk.setNik(nik);
		
		citizenDAO.addPenduduk(penduduk);
		model.addAttribute("penduduk", penduduk);		
		return "suksesPenduduk";
	}
	
	@RequestMapping("/keluarga/tambah")
	public String addFamily(Model model) {
		KeluargaModel keluarga = new KeluargaModel();
		List<KotaModel> kota = citizenDAO.selectAllKota();
		List<KecamatanModel> kecamatan = citizenDAO.selectAllKecamatan();
		List<KelurahanModel> kelurahan = citizenDAO.selectAllKelurahan();
		model.addAttribute("kota", kota);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("keluarga", keluarga);
		return "formAddKeluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String addFamilySubmit(Model model, KeluargaModel keluarga) {
		String nkk = "";
		String kode = keluarga.getKode_kecamatan();
		String kode_provinsi = kode.substring(0, 2);
		String kode_kota = kode.substring(2, 4);
		String kode_kecamatan = kode.substring(4, 6);
		nkk = kode_provinsi + kode_kota + kode_kecamatan;
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String format_date = format.format(date);
		String dd_tanggal_lahir = format_date.substring(0, 2);
		String mm_tanggal_lahir = format_date.substring(3, 5);
		String yy_tanggal_lahir = format_date.substring(8, 10);
		nkk = nkk + dd_tanggal_lahir + mm_tanggal_lahir + yy_tanggal_lahir;
		
		int i = 1;
		String kesamaan = "000" + i;
		KeluargaModel cek_kesamaan_nkk = citizenDAO.selectKeluarga(nkk + kesamaan);
		while (cek_kesamaan_nkk != null) {
			i++;
			if(i <= 9) {
				kesamaan = "000" + i;
			} else if (i > 9 && i <= 99) {
				kesamaan = "00" + i;
			} else if (i > 99 && i <= 999) {
				kesamaan = "0" + i;
			} else {
				kesamaan = "" + i;
			}
			cek_kesamaan_nkk = citizenDAO.selectKeluarga(nkk + kesamaan);
		}
		
		List<KeluargaModel> total_keluarga = citizenDAO.selectAllKeluarga();
		keluarga.setId(total_keluarga.size()+1);
		
		nkk = nkk + kesamaan;
		keluarga.setNomor_kk(nkk);
		keluarga.setId_kelurahan(keluarga.getId_kelurahan());
		keluarga.setIs_tidak_berlaku(0);
		
		citizenDAO.addKeluarga(keluarga);

		model.addAttribute("status", "keluarga");
		model.addAttribute("keluarga", nkk + kesamaan);
		
		return "suksesKeluarga";
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
	public String updateCitizen(Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = citizenDAO.selectPenduduk(nik);
		
		List<PendudukModel> total_penduduk = citizenDAO.selectAllPenduduk();
		penduduk.setId(total_penduduk.size()+1);
		
		model.addAttribute("penduduk", penduduk);
		return "formUpdatePenduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String updateCitizenSubmit(Model model, PendudukModel penduduk) {
		String nik_current = penduduk.getNik();
		penduduk = citizenDAO.selectPenduduk(nik_current);
		System.out.println(penduduk);
		
		String kode_kecamatan = penduduk.getKode_kecamatan();
		kode_kecamatan = kode_kecamatan.substring(0, kode_kecamatan.length()-1);
		String nik = "";
		String tanggal_lahir = penduduk.getTanggal_lahir();
		String yy_tanggal_lahir = tanggal_lahir.substring(2, 4);
		String mm_tanggal_lahir = tanggal_lahir.substring(5, 7);
		String dd_tanggal_lahir = tanggal_lahir.substring(8, 10);
		
		if (penduduk.getJenis_kelamin() == 1) {
			int int_dd_tanggal_lahir = Integer.parseInt(dd_tanggal_lahir);
			dd_tanggal_lahir = "" + (int_dd_tanggal_lahir + 40);
		}
		nik = kode_kecamatan + dd_tanggal_lahir + mm_tanggal_lahir + yy_tanggal_lahir;
		
		int index = 1;
		String cekKesamaan = "000" + index;
		PendudukModel cek_kesamaan_nik = citizenDAO.selectPenduduk(nik + cekKesamaan);
		while (cek_kesamaan_nik != null) {
			index++;
			if(index <= 9) {
				cekKesamaan = "000" + index;
			} else if (index > 9 && index <= 99) {
				cekKesamaan = "00" + index;
			} else if (index > 99 && index <= 999) {
				cekKesamaan = "0" + index;
			} else {
				cekKesamaan = "" + index;
			}
			cek_kesamaan_nik = citizenDAO.selectPenduduk(nik + cekKesamaan);
		}
		nik = nik + cekKesamaan;
		System.out.println(penduduk);
		penduduk.setNik(nik);
		penduduk.setNik_current(nik_current);
		
		citizenDAO.updatePenduduk(penduduk);
		model.addAttribute("penduduk", penduduk);
		
		return "suksesUpdatePenduduk";
	}
}