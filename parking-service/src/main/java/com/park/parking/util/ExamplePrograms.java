package com.park.parking.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExamplePrograms {
	
	public static void main(String args[]) {
		
		//List of Strings 
		//itereate list with stream
		//group by string
		//collect to map
		List<String> apiLists = new ArrayList<>(
				List.of("t1 api/a 80" , "t1 api/a 80", "t3 api/b 100"));
		String s1 = "t3 api/b 100";
		
		s1.chars()
		.mapToObj(c -> (char) c)
		.collect(Collectors.groupingBy(c -> c,LinkedHashMap :: new ,
				Collectors.counting())).entrySet().forEach(System.out::println);
		
		
		Map<Object, Long> apiMap = apiLists.stream()
		.collect(Collectors.groupingBy(s -> s,
				LinkedHashMap :: new ,Collectors.counting()));
		
		apiMap.entrySet().forEach(System.out::println);
		
		//select departmentName,count(*) from employee group by department having count > 1;
		
		
		
	}
	
	
}
