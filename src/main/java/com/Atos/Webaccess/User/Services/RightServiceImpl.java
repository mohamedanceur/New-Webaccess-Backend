package com.Atos.Webaccess.User.Services;

//import static com.atos.security.constants.AppConstants.DOMAIN_CODE_BANQUE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.Atos.Webaccess.User.Entities.Right;
import com.Atos.Webaccess.User.Enums.RightType;
import com.Atos.Webaccess.User.Repositories.RightRepository;

@Service
public class RightServiceImpl implements RightService {
	
	private final RightRepository rightRepository;
	
	@Value("${domain.codeBanque}")
	private String codeBanque;

    @Autowired
    public RightServiceImpl(RightRepository rightRepository) {
        this.rightRepository = rightRepository;
    }




	@Override
	public void initialize() {
		
		
		
		System.out.println("RightServiceImpl");
		List<Right> list_rights = rightRepository.findAll();
		List<String> list_right_descriptions = new ArrayList<String>();
		for (Right r : list_rights) list_right_descriptions.add(r.getDescription());
		
		for (RightType rightType : RightType.values()) {
			if (!list_right_descriptions.contains(rightType.name())) {
				if (rightType.name().equals("MODULE_BENEFICIAIRE"))
				{
					if (this.codeBanque.equals("037") || this.codeBanque.equals("001")) {
						Right right = new Right();
						right.setDescription(rightType.name());
						right.setValue("true");
						right.setStatus(1);
						
						

						
						this.rightRepository.save(right);
					}
				}
				else if (rightType.name().equals("MODULE_CORRESPONDANCES") || rightType.name().equals("BLOCK_SCAN_NUMERISE"))
				{
					if (this.codeBanque.equals("001")) {
						Right right = new Right();
						right.setDescription(rightType.name());
						right.setValue("true");
						right.setStatus(1);
						
						this.rightRepository.save(right);
					}
				}
				else if (rightType.name().equals("MODULE_RCD"))
				{
					if (this.codeBanque.equals("032")) {
						Right right = new Right();
						right.setDescription(rightType.name());
						right.setValue("true");
						right.setStatus(1);
						
						this.rightRepository.save(right);
					}
				}
				else {
					Right right = new Right();
					right.setDescription(rightType.name());
					right.setValue("true");
					right.setStatus(1);
					
					this.rightRepository.save(right);
				}
			
			}
		}
		this.rightRepository.flush();

	
	}




	@Override
	public List<Right> findAll() {
		// TODO Auto-generated method stub
		return this.rightRepository.findAll();
	}
	
	

}
