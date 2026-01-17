import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;

public class Jogo{

    //Variaveis estaticas
    static int ponto1 = 0;
    static int ponto2 = 0;
    static int erro = 0;

    //Método para limpar tela
    static void limparTela(){
        String os = System.getProperty("os.name");
        try{
            if(os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else{
                Runtime.getRuntime().exec("clear");
            }
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }   
    }

    //Método para exibir a interface do jogo
    static void interfaceJogo(char[] palavra){

        //Inicialização das partes do corpo do boneco
        char cabeca = ' ', bracoE = ' ', bracoD = ' ', tronco = ' ', pernaE = ' ', pernaD = ' ';

        //Verificação e erros para exibir partes do corpo
        for(int i=0;i<=erro;i++){
            switch (i) {
                case 1:     cabeca = 'O';    break;
                case 2:     tronco = '|';    break;
                case 3:     bracoE = '/';    break;
                case 4:     bracoD = '\\';   break;
                case 5:     pernaE = '/';    break;
                case 6:     pernaD = '\\';   break;
            }
        }

        limparTela();
        //Interface principal
        System.out.println("=====JOGO DA FORCA=====\n");
        System.out.println("[1]Jogador: " + ponto1 + "     [2]Jogador: " + ponto2 + "\n");
        System.out.println("._____");
        System.out.println("|     |");
        System.out.println("|     " + cabeca);
        System.out.println("|    " + bracoE + tronco + bracoD);
        System.out.println("|    " + pernaE + " " + pernaD + "\n");
        System.out.print(" ");

        //Exibição da palavra totalmente/ parcialmente/ não visivel
        for(char c : palavra){
            System.out.print(c + " ");
        }
        System.out.println();
    }


    public static void main(String[] args){
        //Criação do objeto para ler entrada do usuário
        Scanner scanner = new Scanner(System.in);
        int contadorTurnos = 0;     //Atributo de contagem de turnos
        boolean reinicioJogo = true;    //Atributo para logica de atualização de interface

        do { 
            limparTela();
            System.out.println("=====JOGO DA FORCA=====\n");
            System.out.print("Palavara secreta: ");
            String palavra = scanner.nextLine().toUpperCase();  //Atributição de nome apartir do objeto

            //Criação de um Array de letras a partir da palavra escolhida
            char[] letras = palavra.toCharArray();
            //Criação de um Array com a mesma qquantidade de caracteres do array letras
            char[] palavraOculta = new char[palavra.length()];

            //Atribuição de caracteres especiais e ocutação das letras
            for(int i=0;i<palavra.length();i++){
                if(letras[i] == '-' || letras[i] == ' ' || letras[i] == '\''){
                    palavraOculta[i] = letras[i];
                }else{
                    palavraOculta[i] = '_';
                }
            }

            //Atributo para a a verificação de erros destinado a exibção do boneco
            erro = 0;
            
            //Atributo da lógica do loop principal do jogo
            boolean jogoAtivo = true;
            //loop principal
            while(jogoAtivo){
                limparTela();   //Chama método para limpar tela e ocultar a palavra ecolhida pelo usuário
                interfaceJogo(palavraOculta);   //chama o método para criar a interface

                //Inicio do teste de letras
                System.out.print("\nDigite uma letra: ");
                char letraTentativa = scanner.next().toUpperCase().charAt(0);
                scanner.nextLine();     //Limpa bufer

                //Atributo para determinar a validação de erro
                boolean acerto = false;
                
                //Verificação / comparação da letra digitada e o array de letras 
                for(int i=0;i<palavra.length();i++){
                    if(letras[i] == letraTentativa){
                        palavraOculta[i] = letras[i];
                        acerto = true;
                    }
                }

                //Contagem de erros
                if(!acerto){
                    erro++;
                    System.out.println("Letra nao encontrada.");
                }

                //Verificação de vitoria 
                if(Arrays.equals(palavraOculta, letras)){

                    //Contagem de pontos de acordo com cada jogador
                    if((contadorTurnos %2) == 0){
                        ponto1++;
                    }else{
                        ponto2++;
                    }

                    //Contagem de turnos para a mudança do jogador
                    contadorTurnos++;

                    limparTela();   //Método para limpar tela
                    interfaceJogo(palavraOculta);   //Método para exibir a interface
                    System.out.println("Parabens voce ganhou");
                    jogoAtivo = false;  //Finalização do loop principal
                    
                }else if(erro == 6){    //Derrota

                    //Contagem de turnos para a mudança do jogador
                    contadorTurnos++;
                    limparTela();   //Método para limpar tela
                    interfaceJogo(palavraOculta);   //Método para exibir a interface
                    System.out.println("Voce perdeu a palavra era: " + palavra);
                    jogoAtivo = false;  //Finalização do loop principal
                }
            }

            //Atributo para a verificação de reinicio de jogo
            char opcao;
            do{
                System.out.println("Deseja jogar novamente? [S/n]: ");
                opcao = scanner.next().toUpperCase().charAt(0); 
                scanner.nextLine();     //Limpa bufer
                switch (opcao) {
                    case 'N':
                        reinicioJogo = false;   //sai do jogo por finalização do loop
                        break;
                    case 'S':
                        limparTela();   //Limpa a tela para reexibição da interface
                        break;
                    default:
                        //Reinicia o loop de escolha
                        System.out.println("Opcao invalida. Tente novamente");
                        break;
                }
            }while (opcao != 'S' && opcao != 'N');
            
        } while (reinicioJogo);
        
        scanner.close();    //Fecha a entrada do usuário
    }
}