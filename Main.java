package com.Implementation;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		CustomHashMap<String, String> currentHashMap = new CustomHashMap<String, String>();
		CustomHashMap<String, String> newHashMap = null;

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		while (true) {
			try {
				System.out.println("Enter the function");
				String str = sc.nextLine();
				String[] strSplit = str.split(" ");
				int splitLength = strSplit.length;
				String function = strSplit[0].toUpperCase();

				if (function.equals("SET") || function.equals("GET") || function.equals("UNSET")) {
					if (splitLength != 3) {
						Main.syntax();
						continue;
					}

				} else if (function.equals("UPDATE") || function.equals("COUNT") || function.equals("STRLEN")) {
					if (splitLength != 3) {
						Main.syntax();
						continue;
					}
				} else if (function.equals("BEGIN") || function.equals("ROLLBACK") || function.equals("COMMIT")
						|| function.equals("COMMIT")) {
					if (splitLength != 1) {
						Main.syntax();
						continue;
					}
				}
				switch (function) {

				case "SET":
					if (flag) {
						newHashMap.set(strSplit[1], strSplit[splitLength - 1]);
					} else
						currentHashMap.set(strSplit[1], strSplit[splitLength - 1]);
					break;
				case "GET":
					if (flag) {
						if (newHashMap.get(strSplit[splitLength - 1]) != null) {

							System.out.println(newHashMap.get(strSplit[splitLength - 1]));
						}

						else if (currentHashMap.get(strSplit[splitLength - 1]) == null
								|| newHashMap.get(strSplit[splitLength - 1]) == null)
							System.out.println("null");
					} else {

						System.out.println(currentHashMap.get(strSplit[splitLength - 1]));
					}
					break;
				case "UNSET":
					if (flag) {
						newHashMap.unset(strSplit[splitLength - 1]);

					} else
						currentHashMap.unset(strSplit[splitLength - 1]);

					break;
				case "UPDATE":
					if (flag) {
						boolean isUpdated = newHashMap.update(strSplit[1], strSplit[splitLength - 1]);

						if (!isUpdated)
							System.out.println("no variable named  " + strSplit[1]);

					} else {
						boolean isUpdated = currentHashMap.update(strSplit[1], strSplit[splitLength - 1]);
						if (!isUpdated)
							System.out.println("no variable named  " + strSplit[1]);
					}
					break;
				case "COUNT":
					if (flag) {
						int count = newHashMap.count(strSplit[splitLength - 1]);
						System.out.println(count == 0 ? "NULL" : count);
					} else {
						int count = currentHashMap.count(strSplit[splitLength - 1]);
						System.out.println(count == 0 ? "NULL" : count);
					}
					break;
				case "BEGIN":
					flag = true;
					if (newHashMap != null) {
						currentHashMap = newHashMap.commit(newHashMap, currentHashMap);
					}

					newHashMap = new CustomHashMap<String, String>();
					newHashMap = currentHashMap.backup(newHashMap, currentHashMap);
					break;

				case "ROLLBACK":

					flag = false;
					newHashMap = null;
					break;

				case "COMMIT":

					currentHashMap = newHashMap.commit(newHashMap, currentHashMap);
					break;

				case "GETALLKEY":
					if (newHashMap != null) {
						newHashMap.display();
					} else {
						currentHashMap.display();
					}
					break;
				case "STRLEN":
					if (flag) {
						if (newHashMap.valueLength(strSplit[splitLength - 1]) != 0) {

							System.out.println(newHashMap.valueLength(strSplit[splitLength - 1]));
						}

						else if (currentHashMap.valueLength(strSplit[splitLength - 1]) == 0
								|| newHashMap.valueLength(strSplit[splitLength - 1]) == 0)
							System.out.println("value not found");
					} else {
						if (currentHashMap.valueLength(strSplit[splitLength - 1]) != 0) {
							System.out.println(currentHashMap.valueLength(strSplit[splitLength - 1]));
						} else if (currentHashMap.valueLength(strSplit[splitLength - 1]) == 0) {
							System.out.println("value not found");
						}
					}
					break;

				default:
					System.out.println("Please enter valid command: SET, GET, UNSET, UPDATE, BEGIN, ROLLBACK, COMMIT");
					break;
				}

			} catch (Exception e) {
				System.out.println(e);

			}
		}
	}

	public static void syntax() {

		System.out.println("Follow proper syntax");
		System.out.println(" SET or UNSET or UPDATE (variableName) (value)");
		System.out.println(" GET (variableName) or STRLEN");
		System.out.println(" COUNT (value)");
		System.out.println(" BEGIN or ROLLBACK or COMMIT or GETALLKEY");

	}

}
