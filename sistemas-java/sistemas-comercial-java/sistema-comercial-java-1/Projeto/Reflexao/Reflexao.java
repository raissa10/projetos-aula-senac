/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reflexao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Gelvazio Camargo
 */
public class Reflexao {

    public Reflexao(Class <?> classe) throws Exception {
//        Contato c = new Contato("Felipe", 20, "fesaab@gmail.com");
//        infoClasse(c.getClass());
//        infoObjeto(c);
//        executaMetodos(c);
    }
    
    public void ReflexaoAtual() throws Exception {
        Contato c = new Contato("Felipe", 20, "fesaab@gmail.com");
        infoClasse(c.getClass());
        infoObjeto(c);
        executaMetodos(c);
    }

    public void infoClasse(Class<?> classe) {
        //percorrendo as variaveis de instancia
        for (Field f : classe.getDeclaredFields()) {
            System.out.println(f.getName());
        }
        //percorrendo os metodos
        for (Method m : classe.getDeclaredMethods()) {
            System.out.print(m.getReturnType().getName() + " ");

            System.out.print(m.getName() + " ");

            Class<?> c[] = m.getParameterTypes();
            if (c.length == 0) {
                System.out.println("()");
            } else {
                System.out.print("(");
                for (Class<?> cl : c) {
                    System.out.print(cl.getName() + ",");
                }
                System.out.println(")");
            }
        }
    }

    public void infoObjeto(Object o) throws Exception {
        Class<?> classe = o.getClass();

        for (Field f : classe.getDeclaredFields()) {
            f.setAccessible(true);
            System.out.println(f.getName() + ":" + f.get(o));
        }
    }

    public void executaMetodos(Object o) throws Exception {
        Class<?> classe = o.getClass();
        for (Method m : classe.getDeclaredMethods()) {
            if (m.getParameterTypes().length == 0) {
                System.out.println("Invocando o metodo: " + m.getName());
                if (m.getReturnType().getName().equals("void")){
                    m.invoke(o, new Object[0]);
                } else {
                    System.out.println(m.invoke(o, new Object[0]));
                }
            }
        }
    }
}
