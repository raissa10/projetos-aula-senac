/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
projeto de exemplo:https://github.com/WilliamPhilippe/Projeto-de-Software.git
 */
package Reflexao;

/**
 *
 * @author Gelvazio Camargo
 */
public class example {
    
//        Pessoa p = new Pessoa();
//        p.setCodigo("10");
//        p.setNome("Jessica");
//        p.setCpf("06102314955");
//        p.setTelefone("88630505");
//        //System.out.println("Codigo:" + p.getCodigo());
//
//        try {
//            Method substringMethod = String.class.getMethod("substring", int.class, Integer.TYPE);
//            substringMethod.invoke("abcdefg", 2, new Integer(5));
//            Method m = p.getClass().getMethod("setCodigo", String.class);
//            m.invoke("15");
//            //System.out.println("Codigo:" + m.get());
//        } catch (Exception e) {
//        }
//        
//        System.out.println("Codigo:" + p.getCodigo());
//        Class cls = p.getClass();
//        Method o = cls.getMethod("getCodigo");        
//        p.setCodigo("10");
//        p.setNome("Jessica");
//        p.setCpf("06102314955");
//        p.setTelefone("88630505");
//        
//        Class classe = p.getClass();
//        for (Method m : classe.getDeclaredMethods()) {
//            if (m.getParameterTypes().length == 0) {
//                System.out.println("Invocando o m�todo: " + m.getName());
//                if (m.getReturnType().getName().equals("void")){
//                    //aqui � o set
//                    m.invoke(o, new Object[0]);
//                } else {
//                    System.out.println(m.invoke(o, new Object[0]));
//                }
//            }
//        }
    //}
    
            //mf.addMenuItem("Novo Processo", this.getClass().getName(), this.getClass().getMethod("eventExec", null));
        //p.getClass().getMethod("nome");
        //Method aMethod[] = p.getClass().getMethods();

//        for (int i = 0; i < aMethod.length; i++) {
//            Method metodoLocal = aMethod[i];
//            //System.out.println("Metodo: " + metodoLocal);
//            //System.out.println("\n Valor: " + p.getClass().getMethod(metodoLocal));
//        }
//
//        Class<Pessoa> classe = Pessoa.class;
        //classe.setCodigo("1");
//        for (Field atributo : classe.getDeclaredFields()) {
//          System.out.println(atributo.getName());      
//        }
        //Recupera o nome da classe
        //System.out.println("Nome da Classe: "+arquivoFromReflection.getClass().getName());
        /*
         * A Classe Method do Reflection nos da a possibilidade de manusear
         * todos os m�todos dentro do objeto carregado 
         * */
//        System.out.println("");
//        System.out.println("M�todos: ");
//        for(Method m : p.getClass().getMethods()){
//            System.out.print("Metodo:" + m.getName()+", \n");
//        }
        /*
         * Vamos agora capturar os atributos da classe. Temos agora outra classe 
         * muito importante para uso do Reflection, a classe Field. Esta nos permite
         * manusear os campos/fields da nossa classe carregada.
         * */
//        System.out.println("");
//        System.out.println("Atributos: ");
//        for(Field f : p.getClass().getDeclaredFields()){
//            System.out.println("Campo:" + f.getName()+", ");
//        }
    
//        for (Method obj : p.getClass().getMethods()) {
//            //System.out.println("Valor:" + obj.getClass().getMethod("getCodigo")+", ");
//            Class cls = p.getClass();
//            Method mtd = cls.getMethod("getCodigo");
//
//            try {
//                //Method mtd = cls.getMethod("setCodigo", Item );
//
//                //System.out.println("teste" + mtd.getString());
//                //mtd.invoke(obj);
//                //System.out.println("Valor:" + mtd.invoke(obj, new Object[] {true}));
//                mtd.invoke(obj, 10);
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (SecurityException e) {
//                e.printStackTrace();
//            }
//        }

            //Method codigo = cls.getMethod("getCodigo");
//        this.executaMetodos(p);
//    }
//
//    public static void executaMetodos(Object o) throws Exception {
        //Pessoa p = new Pessoa();
        
        //Class cls = p.getClass();
}
