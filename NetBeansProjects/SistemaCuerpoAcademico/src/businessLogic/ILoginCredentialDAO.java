/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.LoginCredential;
import log.BusinessException;

/**
 *
 * @author Mariana
 */
public interface ILoginCredentialDAO{
      public LoginCredential searchLoginCredential(LoginCredential credential) throws BusinessException ;
      public boolean registerSuccesful(LoginCredential credential) throws BusinessException;
}
