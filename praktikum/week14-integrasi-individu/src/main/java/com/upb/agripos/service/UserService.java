package com.upb.agripos.service;

import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // PERUBAHAN 1: Ubah return type dari 'boolean' menjadi 'User'
    // Agar LoginView bisa mendapatkan data role (jabatan) user yang login
    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        
        if (user != null) {
            // Cek Password
            if (user.getPassword().equals(password)) {
                return user; // Berhasil login, kembalikan data user lengkap
            }
        }
        return null; // Gagal login (User tak ditemukan atau password salah)
    }

    // PERUBAHAN 2: Tambahkan parameter 'role'
    public void register(String username, String password, String role) throws Exception {
        if (userDAO.getUserByUsername(username) != null) {
            throw new Exception("Username sudah dipakai!");
        }
        
        // Simpan User baru beserta Role-nya (Admin/Kasir)
        userDAO.saveUser(new User(username, password, role));
    }
}