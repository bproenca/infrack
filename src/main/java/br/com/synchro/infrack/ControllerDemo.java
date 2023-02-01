package br.com.synchro.infrack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerDemo {

	private static final Logger log = LoggerFactory.getLogger(ControllerDemo.class);
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/ping")
	public Map<String, Object> ping() {
        log.info("## Ping");
		Map<String, Object> retorno = new HashMap<>();
        retorno.put("endpoint", "ping");
        retorno.put("counter", counter.incrementAndGet());
        retorno.put("when", LocalDateTime.now());
        return retorno;
	}
    
    @GetMapping("/sleep")
	public Map<String, Object> sleep(@RequestParam(value = "time", defaultValue = "2000") Integer time) {
        log.info("## Sleep for {}", time);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(); 
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error("Sleep error", e);
        }
        stopWatch.stop();

		Map<String, Object> retorno = new HashMap<>();
        retorno.put("endpoint", "sleep");
        retorno.put("counter", counter.incrementAndGet());
        retorno.put("when", LocalDateTime.now());
        retorno.put("elapsedTime", stopWatch.getTotalTimeSeconds());
        return retorno;
	}
    
    @GetMapping("/error")
	public String error() {
        log.info("## Error");
        if (counter != null) {
            throw new ExceptionDemo();
        }
        return "n/a error";
	}

    @GetMapping("/exit")
	public String exit() {
        System.exit(0);
        log.info("n/a exit");
        return "n/a exit";
	}
    
    @GetMapping("/memory")
    public void memory() {
        List<Object> list = new ArrayList<Object>();
        int cnt = 0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte b[] = new byte[10485760]; //10mb
            list.add(b);
            printMemoryStatus(++cnt);
        }
    }

    private void printMemoryStatus(int cnt) {
        int mb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        StringBuilder builder = new StringBuilder();
        builder.append("## ").append(cnt);
        builder.append("\tUsed Memory   : " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " mb");
        builder.append("\tFree Memory   : " + runtime.freeMemory() / mb + " mb");
        builder.append("\tTotal Memory  : " + runtime.totalMemory() / mb + " mb");
        builder.append("\tMax Memory    : " + runtime.maxMemory() / mb + " mb");
       log.info(builder.toString());
    }
}