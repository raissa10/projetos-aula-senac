/*
 * Copyright (C) 2015 Gelvazio Camargo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Testes;

import VisaoCadastros.CadPais;
import javax.swing.JFrame;

/**
 *
 * @author Gelvazio Camargo
 */
public class TestePrincipal extends JFrame{
    public static void main(String[] args) {
        CadPais formulario = new CadPais();
        //Panel_Principal.add(formulario);
        formulario.setClosable(true);
        formulario.setIconifiable(true);
        formulario.setResizable(true);
        formulario.setMaximizable(true);
        formulario.setVisible(true);
    }
    
}
