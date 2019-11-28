package negocio.cuestion;

import java.util.List;

public interface SACuestion {
	public List<Cuestion> readAll() throws Exception;
	public Cuestion readRandomQuestion() throws Exception;
	public int alta(Cuestion cuestion) throws Exception;
	public int actualizar(Cuestion cuestion) throws Exception;
	public int baja(Cuestion cuestion) throws Exception;
	
}
