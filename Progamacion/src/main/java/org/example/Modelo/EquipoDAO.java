package org.example.Modelo;

import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static CallableStatement cs;

    public EquipoDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Equipo> obtenerEquipos() throws SQLException {
        ArrayList<Equipo> equipos = new ArrayList<>();
        ps = conn.prepareStatement("select * from equipos");
        rs = ps.executeQuery();

        while(rs.next()) {
            Equipo equipo = crearEquipo(rs);
            equipos.add(equipo);
        }
        return equipos;
    }

    public void altaEquipo(Equipo equipo) throws SQLException {
        ps = conn.prepareStatement("insert into equipos (codEquipo, nombre, fechaFundacion) " +
                "values (sec_codEquipo.NEXTVAL, ?, ?)");
        ps.setString(1,equipo.getNombreEquipo());
        ps.setDate(2, parsearFecha(equipo.getFechaFund()));
        ps.executeUpdate();
    }

    public void eliminarEquipo(int codEquipo) throws SQLException {
        ps = conn.prepareStatement("delete from equipos where codEquipo = ?");
        ps.setInt(1, codEquipo);
        ps.executeUpdate();
    }

    public void modificarequipo(String nombreEquipo, LocalDate fechaFundacion) throws SQLException {
        ps=conn.prepareStatement("update equipos set fechaFundacion = ? where nombre = ?");
        ps.setDate(1, parsearFecha(fechaFundacion));
        ps.setString(2, nombreEquipo);
        ps.executeUpdate();
    }

    public Equipo buscarEquipoPorCod(int idEquipo) throws SQLException {
        Equipo equipo = new Equipo();

        ps = conn.prepareStatement("select * from equipos where codEquipo = ?");
        ps.setInt(1, idEquipo);
        rs = ps.executeQuery();

        if(rs.next()) {
            equipo = crearEquipo(rs);
        }
        return equipo;
    }

    public Equipo buscarEquipoPorNombre(String nombre) throws SQLException {
        Equipo equipo = new Equipo();

        ps = conn.prepareStatement("select * from equipos where nombre = ?");
        ps.setString(1, nombre);
        rs=ps.executeQuery();

        if(rs.next()) {
            equipo = crearEquipo(rs);
        }
        return equipo;
    }

    public boolean Existe(String Codequipo) throws SQLException {
        ps = conn.prepareStatement("select * from equipos where nombre = ?");
        ps.setString(1, Codequipo);
        rs = ps.executeQuery();
        if(rs.next()) {
            return true;
        }else return false;
    }

    public void despedirJugador(int codJug) throws SQLException {
        ps = conn.prepareStatement("update jugadores set codEquipo = null where codJugador = ?");
        ps.setInt(1, codJug);
        ps.executeUpdate();
    }

    public void agregarJugador(int jugador, int CodEquip) throws SQLException {
        ps = conn.prepareStatement("UPDATE jugadores SET codEquipo = ? WHERE codJugador = ?");
        ps.setInt(1, CodEquip);
        ps.setInt(2, jugador);
        ps.executeUpdate();

        rs = ps.executeQuery();
    }

    public List<Object[]> getInformeEquipos(int codCompeticion) throws SQLException {
        List<Object[]> lista = new ArrayList<>();

        cs = conn.prepareCall("{ call informeEquiposCompeticion(?, ?) }");
        cs.setInt(1, codCompeticion);
        cs.registerOutParameter(2, OracleTypes.CURSOR);
        cs.execute();

        rs = (ResultSet) cs.getObject(2);

        while (rs.next()) {
            Object[] equipo = new Object[6];
            equipo[0] = rs.getString("NOMBRE");
            equipo[1] = rs.getDate("FECHAFUNDACION").toLocalDate();
            equipo[2] = rs.getInt(3); // Num jugadores
            equipo[3] = rs.getDouble(4); // Salario medio
            equipo[4] = rs.getDouble(5); // Salario Máximo
            equipo[5] = rs.getDouble(6); // Salario Mínimo

            lista.add(equipo);
        }
        rs.close();

        return lista;
    }

//    Funciones privadas
    private Equipo crearEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo(
                rs.getInt("codEquipo"),
                rs.getString("nombre"),
                rs.getDate("fechaFundacion").toLocalDate()
        );
        return equipo;
    }

    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }

}

