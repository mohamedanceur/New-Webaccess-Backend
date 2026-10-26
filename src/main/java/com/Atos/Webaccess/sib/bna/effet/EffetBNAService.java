package com.Atos.Webaccess.sib.bna.effet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*import com.atos.security.domain.agence.Agence;
import com.atos.security.domain.cheques.Cheque;
import com.atos.security.domain.effets.Effet;*/

public interface EffetBNAService {

	public List<EffetBNA> list_rech_op_bkfr(String where_clause);


}
