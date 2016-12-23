package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe responsável por salvar arquivos no servidor
 * @author Bruno Arcanjo
 *
 */

@Component
public class FileSaver {
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * Faz a transferência do arquivo e retornará o caminho onde o arquivo foi salvo
	 * @param baseFolder O local onde o arquivo será salvo 
	 * @param file O arquivo que será salvo
	 * @return O caminho onde o arquivo foi salvo
	 */
	public String writer(String baseFolder, MultipartFile file) {
		if (!file.isEmpty() && (null != baseFolder && !baseFolder.trim().isEmpty())){
			try {
				String realPath = request.getServletContext().getRealPath("/" + baseFolder);
				String path = realPath + "/" + file.getOriginalFilename();
				file.transferTo(new File(path));
				return baseFolder + "/" + file.getOriginalFilename();
			} catch (IllegalStateException | IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
}
