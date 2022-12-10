package com.jawbr;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.hibernate.query.Query;

import com.jawbr.connection.Factory;
import com.jawbr.entity.EquipmentCategory;
import com.jawbr.entity.MagicItems;
import com.jawbr.entity.SourceBook;
import com.jawbr.util.Rarity;

public class MainApp {
	
	public static void main(String[] args) {
		
		Factory factory = new Factory();
		
		boolean isOpen = true;
		try {
			
			while(isOpen) {
				
				// Print tela inicial 
				/*
				 * 1 - Mostrar Itens Mágicos
				 * 2 - Criar Item Mágico
				 * 3 - Atualizar Item Mágico
				 * 4 - Deletar Item Mágico
				 * 0 - Sair
				 * 
				 */
				
				System.out.println("\nItens Mágicos D&D 5E\n");
				
				
				System.out.println("1 - Mostrar Itens Mágicos"
						+"\n2 - Criar Item Mágico"
						+ "\n3 - Atualizar Item Mágico"
						+ "\n4 - Deletar Item Mágico"
						+ "\n0 - Sair");
				
				try {

					System.out.println("\nSelecione uma opção: ");
					int opcao = escolherOpcao();
					
					switch (opcao) {
						case 1: {
							System.out.println("Itens Mágicos: ");
							
							factory.createSession();
							factory.getSession().beginTransaction();
						
							Query<MagicItems> query = factory.getSession().createQuery("select m from MagicItems m", MagicItems.class);
						
							List<MagicItems> items = query.getResultList();
						
							for (MagicItems item : items) {
								System.out.println(MagicItems.chooseItem(item, items.indexOf(item)));
							}
							boolean valid = false;
							while(!valid) {
								
								try {
									
									System.out.println("\nDigite o ID do Item desejado: ");
									opcao = escolherOpcao();
									System.out.println(items.get(opcao-1).printItem());
									valid = true;
									
								} catch (IndexOutOfBoundsException e) {
									System.out.println("Digite um valor valido!");
									for (MagicItems item : items) {
										System.out.println(MagicItems.chooseItem(item, items.indexOf(item)));
									}
								}
								
							}
							
							factory.getSession().getTransaction().commit();
							factory.getSession().close();
							
							break;
						}
						case 2: {
							
							System.out.println("Criando Item Mágico: ");
							
							factory.createSession();
							factory.getSession().beginTransaction();
							
							// Criar entidade do item
							String indexName;
							String name;
							String descr_top;
							String descr_down;
							String rarity;
							String url;
							int equip = 0;
							int source = 0;
							
							System.out.println("\nEscreva o IndexName: ");
							indexName = inputString();
							
							System.out.println("\nEscreva o Nome: ");
							name = inputString();
							
							System.out.println("\nEscreva a Descrição Superior: ");
							descr_top = inputString();
							
							System.out.println("\nEscreva a Descrição Inferior: ");
							descr_down = inputString();
							
							String descr = descr_top + "\n" + descr_down;
							
							System.out.println("\nEscreva a Raridade: ");
							rarity = inputString();
							
							System.out.println("\nEscreva a url: ");
							url = inputString();
							
							System.out.println("\nEscreva o ID da Categoria de Equipamento: ");
							//Puxar todos os tipos de equip category para selecionar
							Query<EquipmentCategory> queryE = factory.getSession().createQuery("select e from EquipmentCategory e", EquipmentCategory.class);
							List<EquipmentCategory> equips = queryE.getResultList();
							
							for(EquipmentCategory e : equips) {
								System.out.println(EquipmentCategory.chooseEquip(e, equips.indexOf(e)));
							}
							
							boolean isValid = true;
							while(isValid) {
								try {
									
									equip = escolherOpcao();
									
									if(equip <= equips.size() || equip >= 1) {
										System.out.println("Opção escolhida : " + equip);
										System.out.println(equips.get(equip-1).printEquip());
										isValid = false;
									}
									
								} catch (IndexOutOfBoundsException e) {
									System.out.println("\nOpção Invalida!\n");
									System.out.println("Escreva o ID da Categoria de Equipamento: ");
								}
							}
							
							System.out.println("\nEscreva o ID do livro fonte: ");
							// Puxar todos os tipos de source book para o usuario selecionar qual que quer
							Query<SourceBook> queryB = factory.getSession().createQuery("select s from SourceBook s", SourceBook.class);
							List<SourceBook> books = queryB.getResultList();
							
							for (SourceBook book : books) {
								System.out.println(SourceBook.chooseBook(book, books.indexOf(book)));
							}
							
							isValid = true;
							while(isValid) {
								try {
									
									source = escolherOpcao();
									
									if(source <= books.size() || source >= 1) {
										System.out.println("Opção escolhida : " + source);
										System.out.println(books.get(source-1).printBook());
										isValid = false;
									}
									
								} catch (IndexOutOfBoundsException e) {
									System.out.println("\nOpção Invalida!\n");
									System.out.println("Escreva o ID do livro fonte: ");
								}
							}	
							
							// criando o item e associando equipement e source book
							MagicItems item = new MagicItems(indexName, name, descr, rarity, url, equips.get(equip-1), books.get(source-1));
							
							System.out.println("Item sendo Criado: \n" + item.printItem());
							
							// salvando item no db
							System.out.println("\nSALVANDO...");
							factory.getSession().save(item);
							
							factory.getSession().getTransaction().commit();
							factory.getSession().close();
							
							System.out.println("\nItem Salvo!");
							break;
						}
						case 3: {
							System.out.println("Atualizando Item Mágico");
							
							
							factory.createSession();
							factory.getSession().beginTransaction();
						
							// Listar todos os itens magicos
							Query<MagicItems> query = factory.getSession().createQuery("select m from MagicItems m", MagicItems.class);
						
							List<MagicItems> items = query.getResultList();
						
							
							
							MagicItems updatedItem;
							
							// Selecionar item magico que queira atualizar, tendo opção de escolher o que quer atualizar no item
							boolean valid = false;
							
							while(!valid) {
								try {
									System.out.println("Selecione o Item que deseja modificar:");
									
									for (MagicItems item : items) {
										System.out.println(MagicItems.chooseItem(item, items.indexOf(item)));
									}
									
									System.out.println("\nDigite o ID do item que deseja atualizar: ");
									opcao = escolherOpcao();
									System.out.println(items.get(opcao-1).printItem());
									updatedItem = items.get(opcao-1);
									
									System.out.println("O que deseja atualizar no item " + updatedItem.getName() + " ?");
									
									System.out.println("\n1 - Nome");
									System.out.println("2 - Descrição Superior");
									System.out.println("3 - Descrição Inferior");
									System.out.println("4 - Raridade");
									System.out.println("5 - Categoria do Equipamento");
									System.out.println("6 - Livro Fonte");
									System.out.println("7 - Nome do Index");
									System.out.println("8 - URL");
									System.out.println("0 - Voltar");
									
									System.out.println("\nDigite o que deseja: ");
									opcao = escolherOpcao();
									
									switch (opcao) {
									case 1: {
										
										System.out.println("Atualizando o Nome do Item Mágico " + updatedItem.getName());
										
										System.out.println("Digite o novo Nome: ");
										String newName = inputString();
										
										System.out.println("Você vai atualizar o Item Mágico de Nome " + updatedItem.getName() + " para " + newName);
										System.out.println("Você realmente deseja fazer essa mudança ? Se sim, digite 1.");
										
										opcao = escolherOpcao();
										if(opcao != 1) {
											System.out.println("Voltando para o Menu!");
											valid = true;
											break;
										}
										
										System.out.println("Atualizando " + updatedItem.getName() + " para " + newName);
										updatedItem.setName(newName);
										valid = true;
										break;
										
									}
									case 2: {
										
										System.out.println("Atualizando a Descrição Superior " + updatedItem.getDescr_top());
										
										System.out.println("Digite a nova Descrição Superior: ");
										String newDescr_top = inputString();
										
										System.out.println("Você vai atualizar o Item Mágico " + updatedItem.getName() + " com a Descrição Superior de " 
										+ updatedItem.getDescr_top() + " para " + newDescr_top);
										
										System.out.println("Você realmente deseja fazer essa mudança ? Se sim, digite 1.");
										
										opcao = escolherOpcao();
										if(opcao != 1) {
											System.out.println("Voltando para o Menu!");
											valid = true;
											break;
										}
										
										System.out.println("Atualizando " + updatedItem.getDescr_top() + " para " + newDescr_top);
										updatedItem.setDescr_top(newDescr_top);
										
										// Juntar as descrições para enviar ao banco de dados ... Possivelmente irei fazer uma Column nova dentro do banco de dados para separar essa descricao para facilitar
										String descr = newDescr_top + "\n" + updatedItem.getDescr_down();
										
										updatedItem.setDescr(descr);
										
										valid = true;
										break;
										
									}
									case 3: {
										
										System.out.println("Atualizando a Descrição Inferior " + updatedItem.getDescr_down());
										
										System.out.println("Digite a nova Descrição Inferior: ");
										String newDescr_down = inputString();
										
										System.out.println("Você vai atualizar o Item Mágico " + updatedItem.getName() + " com a Descrição Inferior de " 
										+ updatedItem.getDescr_down() + " para " + newDescr_down);
										
										System.out.println("Você realmente deseja fazer essa mudança ? Se sim, digite 1.");
										
										opcao = escolherOpcao();
										if(opcao != 1) {
											System.out.println("Voltando para o Menu!");
											valid = true;
											break;
										}
										
										System.out.println("Atualizando " + updatedItem.getDescr_down() + " para " + newDescr_down);
										updatedItem.setDescr_down(newDescr_down);
										
										// Juntar as descrições para enviar ao banco de dados ... Possivelmente irei fazer uma Column nova dentro do banco de dados para separar essa descricao para facilitar
										String descr = updatedItem.getDescr_top() + "\n" + newDescr_down;
										
										updatedItem.setDescr(descr);
										
										valid = true;
										
										break;
										
									}
									case 4: {
										
										System.out.println("Atualizando a Raridade " + updatedItem.getRarity());
										
										System.out.println("Digite o ID da Raridade que deseja: ");
										
										for (Rarity rar : Rarity.values()) {
											System.out.println(rar.ordinal()+1 + " - " + rar.name().replaceAll("_", "\s"));
										}
										
										opcao = escolherOpcao();
										if(opcao < 1 || opcao > Rarity.values().length) {
											System.out.println("Voltando para o Menu!");
											break;
										}
										
										Rarity rar = Rarity.values()[opcao-1];
										
										System.out.println("Você vai atualizar a Raridade de " + updatedItem.getName() + " para " + rar.getDisplayName());
										
										System.out.println("Você realmente deseja fazer essa mudança ? Se sim, digite 1.");
										opcao = escolherOpcao();
										if(opcao != 1) {
											System.out.println("Voltando para o Menu!");
											valid = true;
											break;
										}
										
										System.out.println("Atualizando Item Mágico " + updatedItem.getName() + " com Raridade " + updatedItem.getRarity() 
											+ " para a Raridade " +  rar.getDisplayName());
										
										updatedItem.setRarity(rar.getDisplayName());
										
										valid = true;
										
										break;
										
									}
									case 5: {
										
										System.out.println("Atualizando a Categoria do Equipamento de " + updatedItem.getName());
										
										Query<EquipmentCategory> queryE = factory.getSession().createQuery("select e from EquipmentCategory e", EquipmentCategory.class);
										List<EquipmentCategory> equips = queryE.getResultList();
										
										for(EquipmentCategory e : equips) {
											System.out.println(EquipmentCategory.chooseEquip(e, equips.indexOf(e)));
										}
										
										int equip = 0;
										EquipmentCategory newEquip = null;
										
										System.out.println("Digite o ID que deseja: ");
										
										boolean isValid = true;
										while(isValid) {
											try {
												
												equip = escolherOpcao();
												
												if(equip <= equips.size() || equip >= 1) {
													System.out.println("Opção escolhida : " + equip);
													System.out.println(equips.get(equip-1).printEquip());
													newEquip = equips.get(equip-1);
													isValid = false;
												}
												
											} catch (IndexOutOfBoundsException e) {
												System.out.println("\nOpção Invalida!\n");
												System.out.println("Escreva o ID da Categoria de Equipamento: ");
											}
										}
										
										if(equip <= 0 || newEquip == null) {
											System.out.println("ERRO: Voltado para o Menu!");
											break;
										}
										
										System.out.println("Você vai atualizar o Item Mágico " + updatedItem.getName() + " com  Raridade " 
										+ updatedItem.getEquipmentCategory().getName() + " para a Raridade " + newEquip.getName());
										
										updatedItem.setEquipmentCategory(newEquip);
										
										valid = true;
										
										break;
										
									}
									case 6: {
										
										System.out.println("Atualizando o Livro Fonte de " + updatedItem.getName());
										System.out.println("Você vai atualizar o Item Mágico " + updatedItem.getName() + " com Livro Fonte " + updatedItem.getSourceBook().getName());
										
										// Puxar todos os tipos de source book para o usuario selecionar qual que quer
										Query<SourceBook> queryB = factory.getSession().createQuery("select s from SourceBook s", SourceBook.class);
										List<SourceBook> books = queryB.getResultList();
										
										for (SourceBook book : books) {
											System.out.println(SourceBook.chooseBook(book, books.indexOf(book)));
										}
										
										int bookChoice = 0;
										SourceBook newBook = null;
										
										
										
										System.out.println("Digite o ID do Livro Fonte de Deseja: ");
										
										boolean isValid = true;
										while(isValid) {
											
											try {
												
												bookChoice = escolherOpcao();
												
												if(bookChoice <= books.size() || bookChoice >= 1) {
													System.out.println("Opção escolhida : " + bookChoice);
													System.out.println(books.get(bookChoice-1).printBook());
													newBook = books.get(bookChoice-1);
													isValid = false;
												}
												
											} catch (IndexOutOfBoundsException e) {
												System.out.println("\nOpção Invalida!\n");
												System.out.println("Escreva o ID do Livro Fonte: ");
											}
										}
										
										if(bookChoice <=0 || newBook == null) {
											System.out.println("ERRO: Voltado para o Menu!");
											break;
										}
										
										System.out.println("Você vai atualizar o Item Mágico " + updatedItem.getName() + " com Livro Fonte " 
										+ updatedItem.getSourceBook().getName() + " para o Livro Fonte " + newBook.getName());
										
										updatedItem.setSourceBook(newBook);
										
										valid = true;
										
										break;
										
									}
									case 7: {
										
										System.out.println("Atualizando o Nome do Index de " + updatedItem.getIndexname());
										
										System.out.println("Digite o novo Nome do Index: ");
										String newIndexName = inputString();
										
										System.out.println("Deseja realmente fazer essa mudança? Se sim, digite 1.");
										opcao = escolherOpcao();
										if(opcao != 1) {
											System.out.println("Voltando para o Menu!");
											valid = true;
											break;
										}
										
										System.out.println("Atualizando o Item Mágico " + updatedItem.getName() + " com Nome de Index " + updatedItem.getIndexname() + " para " + newIndexName);
										updatedItem.setIndexname(newIndexName);
										
										valid = true;
										
										break;
										
									}
									case 8: {
										
										System.out.println("Atualizando o URL de " + updatedItem.getName());
										
										System.out.println("Digite o novo URL: ");
										String newURL = inputString();
										
										System.out.println("Deseja realmente fazer essa mudança? Se sim, digite 1.");
										opcao = escolherOpcao();
										if(opcao != 1) {
											System.out.println("Voltando para o Menu!");
											valid = true;
											break;
										}
										
										System.out.println("Atualizando o Item Mágico " + updatedItem.getName() + " com URL " + updatedItem.getUrl() + " para " + newURL);
										updatedItem.setUrl(newURL);
										
										valid = true;
										
										break;
										
									}
									case 0: {
										System.out.println("Voltando para o Menu!");
										valid = true;
										break;
									}
									default:
										System.out.println("Digite um Valor valido!");
									}
									
								} catch (IndexOutOfBoundsException e) {
									System.out.println("Digite um valor valido!");
								}
							}
							
							factory.getSession().getTransaction().commit();
							factory.getSession().close();
							System.out.println("\nAtualizado!");
							break;
						}
						case 4: {
							
							factory.createSession();
							factory.getSession().beginTransaction();
							
							System.out.println("Deletando Item Mágico: ");
							
							// Listar todos os itens magicos
							Query<MagicItems> query = factory.getSession().createQuery("select m from MagicItems m", MagicItems.class);
						
							List<MagicItems> items = query.getResultList();
							
							MagicItems delItem = null;
							
							boolean isValid = true;
							while(isValid) {
								try {
									
									for (MagicItems item : items) {
										System.out.println(MagicItems.chooseItem(item, items.indexOf(item)));
									}
									
									System.out.println("\nDigite o ID do Item Mágico que deseja deletar: ");
									opcao = escolherOpcao();
									System.out.println(items.get(opcao-1).printItem());
									
									delItem = items.get(opcao-1);
									
									isValid = false;
									
								} catch (IndexOutOfBoundsException e) {
									System.out.println("Digite um valor valido!");
								}
							}
							
							System.out.println("Deseja realmente deletar este Item Mágico ? Se sim, digite 1.");
							opcao = escolherOpcao();
							if(opcao != 1) {
								System.out.println("Voltando para o Menu!");
								break;
							}
							
							System.out.println("Deletando o Item Mágico \n" + delItem.printItem());
							
							factory.getSession().delete(delItem);
							
							factory.getSession().getTransaction().commit();
							factory.getSession().close();
							
							System.out.println("\nDeletado!\n");
							
							break;
							
						}
						case 0: {
							System.out.println("\nSaindo!\n");
							isOpen = false;
						}
					}
					
				} catch (InputMismatchException e) {
					
					System.out.println("\nOpção Invalida!\n");
					
				} catch (NoSuchElementException e) {
					
					System.out.println("\nColoque um valor valido!\n");
					
				}
				
			}
			
		} finally {
			try {
				factory.getSession().close();
				factory.getFactory().close();
			} catch (NullPointerException e) {
				factory.getFactory().close();
			}
			
		}
	}
	
	@SuppressWarnings("resource")
	private static int escolherOpcao() {
		
		Scanner scanner = new Scanner(System.in);
		int opcao = scanner.nextInt();
		return opcao;
		
	}
	
	@SuppressWarnings("resource")
	private static String inputString() {
		
		Scanner scanner = new Scanner(System.in);
		String opcao = scanner.nextLine();
		return opcao;
		
	}

}