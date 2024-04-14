package org.fexisaf.crimerecordmanagementsystem.service.openCaseOnMatctService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.OpenCaseOnMatch;
import org.fexisaf.crimerecordmanagementsystem.repository.OpenCaseOnMatchRepository;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenCaseOnMatchServiceImpl implements OpenCaseOnMatchService{

    private final OpenCaseOnMatchRepository openCaseOnMatchRepository;


    @Override
    public Ok<?> saveData(OpenCaseOnMatch onMatch) {

//                // Create an ArrayList to store complaints
//                ArrayList<String> complaints = new ArrayList<>();
//
//                // Add example complaints
//                complaints.add("Report of a violent altercation in progress");
//                complaints.add("Burglary reported by neighbor");
//                complaints.add("Suspicious activity observed by CCTV cameras");
//                complaints.add("Multiple reports of loud noises and screams coming from a residence");
//                complaints.add("Concerned citizen reporting possible drug dealing activity");
//                complaints.add("Report of a missing person, last seen in a dangerous area");
//                complaints.add("Identity theft reported with suspicious financial transactions");
//                complaints.add("Threatening messages received on social media platforms");
//                complaints.add("Report of a suspicious package left unattended in a public area");
//                complaints.add("Fraudulent activity suspected in online transactions");
//                complaints.add("Domestic violence incident reported by a family member");
//                complaints.add("Stalking behavior observed by the victim");
//                complaints.add("Animal cruelty reported by a witness");
//                complaints.add("Hit and run accident reported by a bystander");
//                complaints.add("Report of a potential hostage situation at a residence");
//                complaints.add("Armed robbery reported by a store employee");
//                complaints.add("Fraudulent use of credit card reported by the cardholder");
//                complaints.add("Gang-related activity reported in a specific neighborhood");
//                complaints.add("Report of a suspicious vehicle parked near a school");
//                complaints.add("Vandalism incident reported by a property owner");
//                complaints.add("Harassment complaint filed by an individual against a coworker");
//                complaints.add("Noise complaint from multiple residents about ongoing disturbance");
//                complaints.add("Allegations of child abuse reported by a concerned teacher");
//                complaints.add("Report of a person behaving erratically and posing a danger to themselves or others");
//                complaints.add("Possible arson reported by a witness");
//                complaints.add("Fraudulent insurance claim suspected by the insurer");
//                complaints.add("Report of a break-in at a vacant property");
//                complaints.add("Report of an abandoned vehicle suspected to be involved in criminal activity");
//                complaints.add("Threatening behavior reported by a victim against their ex-partner");
//                complaints.add("Report of a large gathering violating public health regulations");
//                complaints.add("Suspicious activity reported at a construction site during off-hours");
//                complaints.add("Allegations of elder abuse reported by a concerned relative");
//                complaints.add("Report of a drug overdose requiring medical attention");
//                complaints.add("Fraudulent activity detected in banking transactions");
//                complaints.add("Report of an individual making threats with a weapon");
//                complaints.add("Identity theft reported with unauthorized access to personal information");
//                complaints.add("Report of a suspicious individual loitering near a school");
//                complaints.add("Hate crime reported against a specific demographic group");
//                complaints.add("Suspected human trafficking activity reported by a witness");
//                complaints.add("Report of a potential suicide threat made online");
//                complaints.add("Report of an abandoned backpack in a public transit area");
//                complaints.add("Assault incident reported with injuries sustained");
//                complaints.add("Illegal dumping of hazardous materials reported by a witness");
//                complaints.add("Suspicious activity reported near a critical infrastructure facility");
//                complaints.add("Report of a person exhibiting signs of mental distress and potentially dangerous behavior");
//                complaints.add("Harassment complaint filed against a landlord by a tenant");
//                complaints.add("Report of a vehicle driving recklessly and endangering other motorists");
//                complaints.add("Report of a suspicious package delivered to a residence");
//                complaints.add("Allegations of workplace discrimination reported by an employee");
//                complaints.add("Report of an individual trespassing on private property");
//                complaints.add("Report of a minor witnessing parental abuse at home");
//                complaints.add("Report of a disturbance at a public event or gathering");
//                complaints.add("Suspicious activity reported involving drones flying near sensitive areas");
//                complaints.add("Report of an individual exhibiting threatening behavior towards public officials");
//                complaints.add("Suspected child endangerment reported by a concerned neighbor");
//                complaints.add("Report of a person making violent threats over the phone");
//                complaints.add("Suspected cyberbullying incident reported by a student");
//                complaints.add("Report of a possible hate speech incident in public");
//                complaints.add("Allegations of sexual harassment reported by an employee");
//                complaints.add("Report of a suspicious individual loitering near a bank");
//                complaints.add("Suspicious activity reported involving a potential kidnapping attempt");
//                complaints.add("Report of a drone flying in restricted airspace without authorization");
//                complaints.add("Allegations of corruption reported against public officials");
//                complaints.add("Report of a suspicious vehicle following a pedestrian");
//                complaints.add("Suspicious activity reported involving individuals wearing masks and gloves");
//                complaints.add("Report of an individual brandishing a weapon in public");
//                complaints.add("Allegations of fraud reported against a financial institution");
//                complaints.add("Report of a hate crime vandalism incident targeting a religious institution");
//                complaints.add("Suspicious activity reported involving individuals conducting surveillance");
//                complaints.add("Report of a person displaying erratic behavior at a public transit station");
//                complaints.add("Allegations of workplace harassment reported by an employee");
//                complaints.add("Report of a possible drug lab operating in a residential area");
//                complaints.add("Suspicious activity reported involving individuals tampering with security cameras");
//                complaints.add("Report of a person being followed by unknown individuals");
//                complaints.add("Allegations of illegal gambling reported in a specific location");
//                complaints.add("Report of a suspicious person attempting to gain unauthorized access to a building");
//                complaints.add("Suspicious activity reported involving individuals exchanging large sums of cash");
//                complaints.add("Report of a person exhibiting aggressive behavior towards pedestrians");
//                complaints.add("Allegations of animal cruelty reported against a pet owner");
//                complaints.add("Report of a suspicious individual loitering near a playground");
//                complaints.add("Suspicious activity reported involving individuals wearing masks and hoods");
//                complaints.add("Report of a person making threats against public transportation facilities");
//                complaints.add("Allegations of public indecency reported in a public park");
//                complaints.add("Report of a suspicious vehicle parked near a government building");
//                complaints.add("Suspicious activity reported involving individuals conducting illegal dumping");
//                complaints.add("Report of a person engaging in threatening behavior towards school staff");
//                complaints.add("Allegations of illegal drug sales reported in a specific neighborhood");
//                complaints.add("Report of a suspicious person peering into windows of residential properties");
//                complaints.add("Suspicious activity reported involving individuals loitering in a parking lot");
//                complaints.add("Report of a person exhibiting unusual behavior at a shopping mall");
//                complaints.add("Allegations of workplace violence reported by an employee");
//                complaints.add("Report of a suspicious vehicle driving slowly through a neighborhood");
//                complaints.add("Suspicious activity reported involving individuals soliciting donations for fake charities");
//                complaints.add("Report of a person loitering near a school and approaching children");
//                complaints.add("Allegations of public intoxication reported in a city center");
//                complaints.add("Report of a suspicious package left unattended on public transportation");
//                complaints.add("Suspicious activity reported involving individuals carrying firearms");
//                complaints.add("Report of a person making threats against a religious institution");
//
//
//    for(String complaint : complaints){
//        OpenCaseOnMatch onMatch1 = new OpenCaseOnMatch(complaint);
//    openCaseOnMatchRepository.save(onMatch1);
//
//    }
        openCaseOnMatchRepository.save(onMatch);

        return Ok.builder()
                .date(LocalDateTime.now())
                .message("matched save successful...")
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .build();
    }

    @Override
    public boolean findAllService(ComplainEntity complaint) {

        List<OpenCaseOnMatch> sentences = openCaseOnMatchRepository.findByToMatchContaining(complaint.getCrimeType());
//        complaint.setAddressed(true);
        return !sentences.isEmpty();

    }
}
