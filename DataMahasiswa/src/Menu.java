import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 580);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    // id mahasiswa yang sedang dipilih
    private int selectedId = -1;

    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel prodiLabel;
    private JComboBox prodiComboBox;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box jenis kelamin
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // atur isi combo box program studi
        String[] prodiData = {"", "Ilmu Komputer", "Pendidikan Ilmu Komputer"};
        prodiComboBox.setModel(new DefaultComboBoxModel(prodiData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex == -1) {
                    // insert - validasi input kosong dan NIM unik
                    if(isInputValid() && isNimUnique()) {
                        insertData();
                    }
                } else {
                    // update - hanya perlu validasi input kosong
                    if(isInputValid()) {
                        updateData();
                    }
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex >= 0) {
                    // tambah confirmation dialog
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Apakah Anda yakin ingin menghapus data ini?",
                            "Konfirmasi Penghapusan",
                            JOptionPane.YES_NO_OPTION
                    );

                    // delete jika user konfirmasi iya
                    if (choice == JOptionPane.YES_OPTION) {
                        deleteData();
                    }
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saat tombol
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                try {
                    // ambil id dari tabel
                    ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa LIMIT " + selectedIndex + ",1");

                    if (resultSet.next()) {
                        // simpan ID untuk keperluan update/delete
                        selectedId = resultSet.getInt("id");

                        // simpan value untuk field dan combo box
                        String selectedNim = resultSet.getString("nim");
                        String selectedNama = resultSet.getString("nama");
                        String selectedJenisKelamin = resultSet.getString("jenis_kelamin");
                        String selectedProdi = resultSet.getString("prodi");

                        // ubah isi textfield dan combo box
                        nimField.setText(selectedNim);
                        namaField.setText(selectedNama);
                        jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                        prodiComboBox.setSelectedItem(selectedProdi);

                        // ubah button "Add" menjadi "Update"
                        addUpdateButton.setText("Update");
                        // tampilkan button delete
                        deleteButton.setVisible(true);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Program Studi"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            // isi tabel dengan listMahasiswa
            int i = 0;
            while(resultSet.next()){
                Object[] row = new Object[5];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("prodi");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return temp;
    }

    public boolean isInputValid() {
        // validasi input kosong
        if(nimField.getText().isEmpty() || namaField.getText().isEmpty() ||
                jenisKelaminComboBox.getSelectedItem().toString().isEmpty() || prodiComboBox.getSelectedItem().toString().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Semua field harus diisi!",
                    "Error: Input Kosong",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    public boolean isNimUnique() {
        try {
            // cek apakah NIM sudah ada di database
            String nim = nimField.getText();
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");

            if(resultSet.next()) {
                JOptionPane.showMessageDialog(
                        null,
                        "NIM " + nim + " sudah terdaftar!",
                        "Error: NIM Duplikat",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String prodi = prodiComboBox.getSelectedItem().toString();

        // tambahkan data ke dalam database
        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + prodi + "');";
        database.insertUpdateDeleteQuery(sql);
        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String prodi = prodiComboBox.getSelectedItem().toString();

        // update data di database berdasarkan ID
        String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama +
                "', `jenis_kelamin` = '" + jenisKelamin + "', prodi = '" + prodi + "' WHERE id = " + selectedId;

        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }

    public void deleteData() {
        // hapus data dari database berdasarkan ID
        String sql = "DELETE FROM mahasiswa WHERE id = " + selectedId;
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        prodiComboBox.setSelectedItem("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
        // reset selectedId
        selectedId = -1;
    }
}
