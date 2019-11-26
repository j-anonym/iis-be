package fit.school.project.iis.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    
    private static final long serialVersionUID = -8091879091924046844L;
    private final String authenticationtoken;
    
    public AuthenticationResponse(String authenticationtoken) {
        this.authenticationtoken = authenticationtoken;
	}

	public String getToken() {
		return this.authenticationtoken;
	}
}